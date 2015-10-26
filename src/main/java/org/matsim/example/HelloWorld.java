/* *********************************************************************** *
 * project: org.matsim.*												   *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2008 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */
package org.matsim.example;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkFactory;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.scenario.ScenarioUtils;

/**
 * @author nagel
 *
 */
public class HelloWorld {

	public static void main(String[] args) {
		
		// This creates a default matsim config:
		Config config = ConfigUtils.createConfig();
		
		config.controler().setLastIteration(1);
		config.controler().setOverwriteFileSetting( OverwriteFileSetting.deleteDirectoryIfExists );

		// This creates a default matsim scenario (which is empty):
		Scenario scenario = ScenarioUtils.createScenario(config);
		
		// create network
		Network network = scenario.getNetwork();
		NetworkFactory nf = network.getFactory();
		
		Id<Link> id = Id.createLinkId("dummyLink1");
		Coord coord1 = new Coord(0, 0);
		Node fromNode = nf.createNode(Id.createNodeId("dummyNode1"), coord1);
		Coord coord2 = new Coord(1, 1);
		Node toNode = nf.createNode(Id.createNodeId("dummyNode2"), coord2 );
		Link link = nf.createLink(id, fromNode, toNode);
		network.addNode(fromNode);
		network.addNode(toNode);
		network.addLink(link );
		

		Controler controler = new Controler( scenario ) ;

		controler.addOverridingModule(new AbstractModule(){

			@Override
			public void install() {
				this.bindMobsim().toProvider(MyMobsimProvider.class);
				this.bind(Scenario.class).to(Scenario.class);
			}
			
		});
		
		controler.setDirtyShutdown(true);
		
		// This indeed runs iterations, but based on an empty scenario:
		controler.run();

	}

}
