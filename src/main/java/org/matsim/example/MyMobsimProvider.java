package org.matsim.example;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.mobsim.framework.Mobsim;
import org.matsim.core.mobsim.qsim.QSim;
import org.matsim.core.mobsim.qsim.QSimUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class MyMobsimProvider implements Provider<Mobsim> {

	@Inject
	private Scenario scenario;
	
	@Inject
	private EventsManager eventsManager;

	@Override
	public Mobsim get() {
		QSim qSim = QSimUtils.createDefaultQSim(scenario, eventsManager);
		return qSim;
	}

}
