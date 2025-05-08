package com.cloud;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.*;

import java.util.*;

public class CloudAllocation {
    public static void main(String[] args) {
        Log.printLine("Starting CloudSim VM Allocation Example...");

        try {
            // Initialize CloudSim
            int numUsers = 1;
            Calendar calendar = Calendar.getInstance();
            boolean traceFlag = false;
            CloudSim.init(numUsers, calendar, traceFlag);

            // Create Datacenter
            Datacenter datacenter = createDatacenter("Datacenter_0");

            // Create Broker
            DatacenterBroker broker = createBroker();
            int brokerId = broker.getId();

            // Create VM List
            List<Vm> vmList = new ArrayList<>();
            vmList.add(new Vm(0, brokerId, 1000, 1, 512, 1000, 10000, "Xen", new CloudletSchedulerTimeShared()));
            vmList.add(new Vm(1, brokerId, 1500, 1, 768, 1500, 15000, "Xen", new CloudletSchedulerTimeShared()));
            broker.submitVmList(vmList);

            // Create Cloudlet List
            List<Cloudlet> cloudletList = new ArrayList<>();
            UtilizationModel utilizationModel = new UtilizationModelFull();

            cloudletList.add(new Cloudlet(0, 400000, 1, 300, 300, utilizationModel, utilizationModel, utilizationModel));
            cloudletList.add(new Cloudlet(1, 200000, 1, 300, 300, utilizationModel, utilizationModel, utilizationModel));

            for (Cloudlet cloudlet : cloudletList) {
                cloudlet.setUserId(brokerId);
            }

            broker.submitCloudletList(cloudletList);

            // Start simulation
            CloudSim.startSimulation();
            CloudSim.stopSimulation();

            // Print Results
            List<Cloudlet> newList = broker.getCloudletReceivedList();
            printCloudletList(newList);

            Log.printLine("CloudSim VM Allocation Example Finished!");

        } catch (Exception e) {
            e.printStackTrace();
            Log.printLine("An error occurred!");
        }
    }

    private static Datacenter createDatacenter(String name) {
        List<Host> hostList = new ArrayList<>();
        List<Pe> peList = new ArrayList<>();

        peList.add(new Pe(0, new PeProvisionerSimple(1000)));
        peList.add(new Pe(1, new PeProvisionerSimple(1500)));

        int hostId = 0;
        int ram = 2048; // Host RAM (MB)
        long storage = 1000000; // Host Storage (MB)
        int bw = 10000; // Bandwidth

        hostList.add(new Host(hostId, new RamProvisionerSimple(ram), new BwProvisionerSimple(bw), storage, peList, new VmSchedulerTimeShared(peList)));

        String arch = "x86";
        String os = "Linux";
        String vmm = "Xen";
        double timeZone = 10.0;
        double costPerSec = 3.0;
        double costPerMem = 0.05;
        double costPerStorage = 0.001;
        double costPerBw = 0.0;

        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(arch, os, vmm, hostList, timeZone, costPerSec, costPerMem, costPerStorage, costPerBw);
        Datacenter datacenter = null;

        try {
            datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), new LinkedList<>(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datacenter;
    }

    private static DatacenterBroker createBroker() {
        DatacenterBroker broker = null;
        try {
            broker = new DatacenterBroker("Broker");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return broker;
    }

    private static void printCloudletList(List<Cloudlet> list) {
        Log.printLine("\n========== CLOUDLET EXECUTION RESULTS ==========");
        Log.printLine("Cloudlet ID | Status | Execution Time | Start Time | Finish Time");

        for (Cloudlet cloudlet : list) {
            String status = (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) ? "SUCCESS" : "FAILED";
            Log.printLine(cloudlet.getCloudletId() + " | " + status + " | " + cloudlet.getActualCPUTime() + " | " + cloudlet.getExecStartTime() + " | " + cloudlet.getFinishTime());
        }
    }
}

