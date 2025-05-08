package com.cloud;

import java.util.*;
import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.*;

public class CloudScheduling {
    
    private static List<Cloudlet> cloudletList;
    private static List<Vm> vmlist;

    // VM and Cloudlet configuration constants
    private static final int VM_MIPS = 1000;
    private static final long VM_SIZE = 10000;  // VM storage (MB)
    private static final int VM_RAM = 512;      // VM memory (MB)
    private static final long VM_BW = 1000;     // Bandwidth (Mbps)
    private static final int VM_PES = 1;
    private static final String VMM = "Xen";
    
    private static final long CLOUDLET_LENGTH = 400000;
    private static final long CLOUDLET_FILE_SIZE = 300;
    private static final long CLOUDLET_OUTPUT_SIZE = 300;

    public static void main(String[] args) {
        Log.printLine("Starting CloudSim FCFS Scheduling...");

        try {
            // Initialize CloudSim
            int numUser = 1;
            Calendar calendar = Calendar.getInstance();
            boolean traceFlag = false;
            CloudSim.init(numUser, calendar, traceFlag);

            // Create Datacenter
            Datacenter datacenter0 = createDatacenter("Datacenter_0");

            // Create Broker
            DatacenterBroker broker = createBroker();
            int brokerId = broker.getId();

            // Create VM list
            vmlist = new ArrayList<>();
            Vm vm = new Vm(0, brokerId, VM_MIPS, VM_PES, VM_RAM, VM_BW, VM_SIZE, VMM, new CloudletSchedulerTimeShared());
            vmlist.add(vm);
            broker.submitVmList(vmlist);

            // Create Cloudlet list
            cloudletList = new ArrayList<>();
            UtilizationModel utilizationModel = new UtilizationModelFull();

            Cloudlet cloudlet1 = new Cloudlet(0, CLOUDLET_LENGTH, VM_PES, CLOUDLET_FILE_SIZE, CLOUDLET_OUTPUT_SIZE, utilizationModel, utilizationModel, utilizationModel);
            cloudlet1.setUserId(brokerId);

            Cloudlet cloudlet2 = new Cloudlet(1, CLOUDLET_LENGTH / 2, VM_PES, CLOUDLET_FILE_SIZE, CLOUDLET_OUTPUT_SIZE, utilizationModel, utilizationModel, utilizationModel);
            cloudlet2.setUserId(brokerId);

            cloudletList.add(cloudlet1);
            cloudletList.add(cloudlet2);
            broker.submitCloudletList(cloudletList);

            // Start simulation
            CloudSim.startSimulation();
            CloudSim.stopSimulation();

            // Print Results
            List<Cloudlet> newList = broker.getCloudletReceivedList();
            printCloudletList(newList);

            Log.printLine("CloudSim FCFS Scheduling finished!");

        } catch (Exception e) {
            e.printStackTrace();
            Log.printLine("An unexpected error occurred!");
        }
    }

    private static Datacenter createDatacenter(String name) {
        List<Host> hostList = new ArrayList<>();
        List<Pe> peList = new ArrayList<>();
        
        // Processing element (CPU core)
        peList.add(new Pe(0, new PeProvisionerSimple(VM_MIPS)));

        // Host specification
        int hostId = 0;
        int hostRam = 2048;  // Host memory (MB)
        long hostStorage = 1000000;  // Storage (MB)
        int hostBw = 10000;  // Bandwidth

        Host host = new Host(hostId, new RamProvisionerSimple(hostRam), new BwProvisionerSimple(hostBw), hostStorage, peList, new VmSchedulerTimeShared(peList));
        hostList.add(host);

        // Datacenter specifications
        String arch = "x86"; 
        String os = "Linux"; 
        double timeZone = 10.0;
        double costPerSec = 3.0;
        double costPerMem = 0.05;
        double costPerStorage = 0.001;
        double costPerBw = 0.0;

        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(arch, os, VMM, hostList, timeZone, costPerSec, costPerMem, costPerStorage, costPerBw);
        
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
        Log.printLine("\n========== OUTPUT ==========");
        Log.printLine("Cloudlet ID | Status | Execution Time | Start Time | Finish Time");

        for (Cloudlet cloudlet : list) {
            String status = cloudlet.getCloudletStatus() == Cloudlet.SUCCESS ? "SUCCESS" : "FAILED";
            Log.printLine(cloudlet.getCloudletId() + " | " + status + " | " + cloudlet.getActualCPUTime() + " | " + cloudlet.getExecStartTime() + " | " + cloudlet.getFinishTime());
        }
    }
}
