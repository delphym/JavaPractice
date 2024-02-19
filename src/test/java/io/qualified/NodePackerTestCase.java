package io.qualified;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.extern.java.Log;

@Log
class NodePackerTestCase {

	private NodePacker nodePacker;

	@BeforeEach
	public void setup() {
		nodePacker = new NodePacker();
	}

	@Test
	@DisplayName("Step 1: No apps means an empty Cluster")
	void step1() throws IllegalAccessException {
		log.info("Step 1: No apps means an empty Cluster");
		NodePacker.Machine machine = new NodePacker.Machine("machine_1", 1200L, 4096L);

		NodePacker.Cluster actualCluster = nodePacker.pack(machine);

		Assertions.assertEquals(0, actualCluster.machines.size());
	}

	@Test
	@DisplayName("Step 2: A single app fits on one machine")
	void step2() throws IllegalAccessException {
		log.info("Step 2: A single app fits on one machine");
		NodePacker.App app = new NodePacker.App("appName", 1000L, 1024L);

		NodePacker.Machine machine = new NodePacker.Machine("machine_1", 1200L, 4096L);

		NodePacker.Cluster actualCluster = nodePacker.pack(machine, app);

		Assertions.assertTrue(assertMachineHas(actualCluster, app));
	}

	@Test
	@DisplayName("Step 3: Two small CPU apps fit on one machine")
	void step3() throws IllegalAccessException {
		log.info("Step 3: Two small CPU apps fit on one machine");
		NodePacker.App app1 = new NodePacker.App("app_1", 500L, 1024L);
		NodePacker.App app2 = new NodePacker.App("app_2", 500L, 1024L);

		NodePacker.Machine machine = new NodePacker.Machine("machine_1", 1200L, 4096L);

		NodePacker.Cluster actualCluster = nodePacker.pack(machine, app1, app2);

		Assertions.assertEquals(1, actualCluster.machines.size());
		Assertions.assertTrue(assertMachineHas(actualCluster, app1, app2));
	}

	@Test
	@DisplayName("Step 4: Two large CPU apps fit on two machines")
	void step4() throws IllegalAccessException {
		log.info("Step 4: Two large CPU apps fit on two machines");
		NodePacker.App app1 = new NodePacker.App("app_1", 1000L, 1024L);
		NodePacker.App app2 = new NodePacker.App("app_2", 1000L, 1024L);

		NodePacker.Machine machine = new NodePacker.Machine("machine_1", 1200L, 4096L);

		NodePacker.Cluster actualCluster = nodePacker.pack(machine, app1, app2);

		Assertions.assertEquals(2, actualCluster.machines.size());
		Assertions.assertTrue(assertMachineHas(actualCluster, app1));
		Assertions.assertTrue(assertMachineHas(actualCluster, app2));
	}

	@Test
	@DisplayName("Step 5: Two large MEM apps fit on two machines")
	void step5() throws IllegalAccessException {
		log.info("Step 5: Two large MEM apps fit on two machines");
		NodePacker.App app1 = new NodePacker.App("app_1", 500L, 3072L);
		NodePacker.App app2 = new NodePacker.App("app_2", 500L, 3072L);

		NodePacker.Machine machine = new NodePacker.Machine("machine_1", 1200L, 4096L);

		NodePacker.Cluster actualCluster = nodePacker.pack(machine, app1, app2);

		Assertions.assertEquals(2, actualCluster.machines.size());
		Assertions.assertTrue(assertMachineHas(actualCluster, app1));
		Assertions.assertTrue(assertMachineHas(actualCluster, app2));
	}

	@Test
	@DisplayName("Step 6: Apps could fit on previous machines that have space")
	void step6() throws IllegalAccessException {
		log.info("Step 6: Apps could fit on previous machines that have space");
		NodePacker.App app1 = new NodePacker.App("app_1", 800L, 2048L);
		NodePacker.App app2 = new NodePacker.App("app_2", 1000L, 3072L);
		NodePacker.App app3 = new NodePacker.App("app_3", 400L, 2048L);

		NodePacker.Machine machine = new NodePacker.Machine("machine_1", 1200L, 4096L);

		NodePacker.Cluster actualCluster = nodePacker.pack(machine, app1, app2, app3);

		Assertions.assertEquals(2, actualCluster.machines.size());
		Assertions.assertTrue(assertMachineHas(actualCluster, app1, app3));
		Assertions.assertTrue(assertMachineHas(actualCluster, app2));
	}

	@Test
	@DisplayName("Step 7: Expect exception when app is too large for any machine")
	void step7() {
		log.info("Step 7: Expect exception when app is too large for any machine");
		NodePacker.App app1 = new NodePacker.App("app_1", 800L, 4096L);

		NodePacker.Machine machine = new NodePacker.Machine("machine_1", 1L, 1L);

		Assertions.assertThrows(IllegalArgumentException.class, () -> nodePacker.pack(machine, app1));
	}

	// Convenience method
	private boolean assertMachineHas(NodePacker.Cluster actualCluster, NodePacker.App... apps) {
		return actualCluster.machines.stream().anyMatch(actualMachine -> actualMachine.apps.size() == apps.length && actualMachine.apps.containsAll(Arrays.asList(apps)));
	}
}
