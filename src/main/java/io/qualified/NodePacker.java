package io.qualified;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import lombok.extern.java.Log;

@Log
class NodePacker {
	static class App {
		String appName;
		Long appMCpu;
		Long appMem;

		public App(String name, Long mCpu, Long mem) {
			appName = name;
			appMCpu = mCpu;
			appMem = mem;
		}

		@Override
		public String  toString() {
			return  "[" + this.appName + ", " + this.appMCpu + ", " + this.appMem + "]";
		}
	}

	static class Machine {
		String machineName;
		ArrayList<App> apps;
		Long mCpu;
		Long mem;

		public Machine(String name, Long cpu, Long memory) {
			machineName = name;
			apps = new ArrayList<>();
			mCpu = cpu;
			mem = memory;
		}

		@Override
		public Machine clone() {
			return new Machine(this.machineName, this.mCpu, this.mem);
		}

		public Long remainingMCpu() {
			AtomicReference<Long> remaining  = new AtomicReference<>(this.mCpu);
			apps.forEach(app -> {
				remaining.set(remaining.get() - app.appMCpu);
			});
			log.info("Remaining CPU:  {0} " + remaining.get());
			return  Math.max(remaining.get(), 0L);
		}
		public Long remainingMem() {
			AtomicReference<Long> remaining  = new AtomicReference<>(this.mem);
			apps.forEach(app -> {
				remaining.set(remaining.get() - app.appMem);
			});
			log.info("Remaining MEM: " + remaining.get());
			return Math.max(remaining.get(), 0L);
		}

		@Override
		public String  toString() {
			return  "["  + this.machineName  +  "@" + Integer.toHexString(hashCode()) + ", spec CPU: " + this.mCpu + ", spec MEM: " + this.mem +  "=>" + this.apps.toString() +
					        ", remaining CPU: " + this.remainingMCpu() + ", remaining MEM: " + remainingMem() + "]";
		}
	}

	static class Cluster {
		ArrayList<Machine> machines;

		public Cluster() {
			machines = new ArrayList<>();
		}
	}

	public Cluster pack(Machine machine, App... appsToPack) throws IllegalAccessException {

		Cluster cluster = new Cluster();
		List<Machine> machines = new ArrayList<>();

		List<App> apps = new ArrayList<>(List.of(appsToPack));          //Need to convert  the appsToPack 'array' into a list which can be modified
		while (!apps.isEmpty()) {
			Machine currentMachine = machine.clone();
			for (App currentApp : new ArrayList<>(apps)) {                      //to work on the already reduced lists of apps
				if (currentApp.appMem > currentMachine.mem || currentApp.appMCpu > currentMachine.mCpu) {
					String msg = "App: " + currentApp + " is too big to fit Machine: " + currentMachine;
					log.info(msg);
					throw new IllegalArgumentException(msg);
				}
				if (appFitsToMachine(currentMachine, currentApp)) {
					currentMachine.apps.add(currentApp);
					log.info("currentMachine state: " + currentMachine);
					apps.remove(currentApp);
				}
			}
			machines.add(currentMachine);
		}

		cluster.machines.addAll(machines);
		return cluster;
	}

	private boolean appFitsToMachine(Machine machine, App app) {
		if (machine.remainingMem() < app.appMem) return false;
		if (machine.remainingMCpu() < app.appMCpu) return false;
		return  true;
	}
}