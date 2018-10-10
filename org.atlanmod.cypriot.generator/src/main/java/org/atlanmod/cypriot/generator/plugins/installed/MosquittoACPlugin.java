package org.atlanmod.cypriot.generator.plugins.installed;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.atlanmod.cypriot.cyprIoT.Bind;
import org.atlanmod.cypriot.cyprIoT.ChannelToBind;
import org.atlanmod.cypriot.cyprIoT.CyprIoTModel;
import org.atlanmod.cypriot.cyprIoT.InstanceThing;
import org.atlanmod.cypriot.cyprIoT.Network;
import org.atlanmod.cypriot.cyprIoT.ToBindPubSub;
import org.atlanmod.cypriot.cyprIoT.Topic;
import org.atlanmod.cypriot.generator.network.NetworkGenerator;
import org.atlanmod.cypriot.generator.network.NetworkGenerator.TopicTypes;
import org.atlanmod.cypriot.generator.plugins.Plugin;
import org.eclipse.emf.common.util.EList;
import org.thingml.xtext.thingML.ThingMLModel;

public class MosquittoACPlugin implements Plugin {
	StringBuilder subTopicsRules = new StringBuilder();
	StringBuilder pubTopicsRules = new StringBuilder();

	@Override
	public String getID() {
		return "mosquitto";
	}

	@Override
	public void attach() {
		System.out.println("Loading Access Control plugin...");
	}

	@Override
	public CyprIoTModel initiliaze() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ThingMLModel> loadModel(CyprIoTModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean validate(ArrayList<ThingMLModel> thingmlModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CyprIoTModel transform(CyprIoTModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generate(CyprIoTModel model, File outputDirectory) {
		EList<Network> allNetworks = model.getSpecifyNetworks();
		for (Network network : allNetworks) {
			EList<Bind> allBinds = network.getHasBinds();
			for (Bind bindPubSub : allBinds) {
					ChannelToBind channelBinding = bindPubSub.getChannelToBind();
					if(channelBinding instanceof ToBindPubSub) {
						
						String pubSubChannelName = ((ToBindPubSub) channelBinding).getTargetedPubSubInstance().getName();
						InstanceThing instanceThing = bindPubSub.getBindsInstanceThing();
						
						ArrayList<Bind> pubSubBindsContainingThingInstances = NetworkGenerator.pubSubBindsContainingThingInstances(instanceThing, network);

						ArrayList<Topic> pubTopics = NetworkGenerator.getAllTopicsOfType(instanceThing, pubSubBindsContainingThingInstances, TopicTypes.PUBTOPIC);
						ArrayList<Topic> subTopics = NetworkGenerator.getAllTopicsOfType(instanceThing, pubSubBindsContainingThingInstances, TopicTypes.SUBTOPIC);
						
						for (Topic pubTopic : pubTopics) {
							StringBuilder pubtopicFull = new StringBuilder();
							if(pubTopic.getSubtopicOf().size()!=0) {
								pubtopicFull.append(pubTopic.getSubtopicOf().get(0).getName()+"/"+pubTopic.getName());
							} else {
								pubtopicFull.append(pubTopic.getName());
							}
							String mosquittoAcl = "user " +instanceThing.getName()+"\n" + "topic write "+pubtopicFull+" \n \n";
							pubTopicsRules.append(mosquittoAcl);
						}
						
						for (Topic subTopic : subTopics) {
							StringBuilder subtopicFull = new StringBuilder();
							if(subTopic.getSubtopicOf().size()!=0) {
								subtopicFull.append(subTopic.getSubtopicOf().get(0).getName()+"/"+subTopic.getName());
							} else {
								subtopicFull.append(subTopic.getName());
							}
							String mosquittoAcl = "user " +instanceThing.getName()+"\n" + "topic read "+subtopicFull+" \n \n";
							subTopicsRules.append(mosquittoAcl);
						}
						
						String mosquittoACLRules = subTopicsRules+""+pubTopicsRules;
						writeToACLFile(outputDirectory, pubSubChannelName, mosquittoACLRules);
					}

			}
		}
	}

	/**
	 * @param outputDirectory
	 * @param pubSubChannelName
	 * @param mosquittoAcl
	 */
	public void writeToACLFile(File outputDirectory, String pubSubChannelName, String mosquittoAcl) {
		String filename = outputDirectory.getParentFile().getAbsolutePath() + "/output/"
				+ pubSubChannelName+"_mosquitto.acl";
		File fileMosquittoAcl = new File(filename);
		if (fileMosquittoAcl.exists()) {
			try
			{
			    FileWriter fw = new FileWriter(filename,false);
			    fw.write(mosquittoAcl);
			    fw.close();
			}
			catch(IOException ioe)
			{
			    System.err.println("IOException: " + ioe.getMessage());
			}
		} else {
			try {
				FileUtils.writeStringToFile(fileMosquittoAcl, mosquittoAcl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void verifiy(CyprIoTModel model) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deploy(CyprIoTModel model) {
		// TODO Auto-generated method stub

	}

}