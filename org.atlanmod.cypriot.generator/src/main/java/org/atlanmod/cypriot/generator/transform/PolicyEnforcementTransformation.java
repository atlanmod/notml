package org.atlanmod.cypriot.generator.transform;

import org.atlanmod.cypriot.cyprIoT.InstanceThing;
import org.atlanmod.cypriot.cyprIoT.Network;
import org.atlanmod.cypriot.cyprIoT.PoliciesEnforcement;
import org.atlanmod.cypriot.cyprIoT.Policy;
import org.atlanmod.cypriot.cyprIoT.Rule;
import org.atlanmod.cypriot.cyprIoT.RuleAction;
import org.atlanmod.cypriot.cyprIoT.RuleEffect;
import org.atlanmod.cypriot.cyprIoT.RuleSubject;
import org.atlanmod.cypriot.cyprIoT.Thing;
import org.atlanmod.cypriot.generator.utilities.NetworkHelper;
import org.eclipse.emf.common.util.EList;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Transition;

public class PolicyEnforcementTransformation implements Transformation {
	RuleEnforcement goToStateEnforce = new GoToStateRule();

	@Override
	public ThingMLModel transform(InstanceThing instanceThing, ThingMLModel modelToTransform) {
		Network network = (Network) instanceThing.eContainer();
		if (isPolicyEnforcement(network)) {
			PoliciesEnforcement policiesEnforcement = network.getHasPolicyEnforcement();
			EList<Policy> enforcedpolicies = policiesEnforcement.getPolicyName();

			for (Policy policy : enforcedpolicies) {
				EList<Rule> rules = policy.getHasRules();
				for (Rule rule : rules) {
					RuleSubject ruleSubject = rule.getRuleSubject();
					if (rule.getRuleEffect().equals(RuleEffect.ALLOW)) {

					} else if (rule.getRuleEffect().equals(RuleEffect.DENY)) {

					}
					if (rule.getRuleEffect().equals(RuleEffect.TRIGGER)) {
						if (rule.getRuleAction().equals(RuleAction.GO_TO_STATE)) {
							if (ruleSubject instanceof Thing) {
								String thingName = ((Thing) ruleSubject).getName();
								if (((InstanceThing) instanceThing).getThingToInstantiate().getName()
										.equals(thingName)) {
									EList<State> allStates = NetworkHelper.getAllStateInThingMLModel(modelToTransform);

									for (State state : allStates) {
										if (!state.getName().equals(rule.getRuleConditions().getState())) {
											Transition transition = ThingMLFactory.eINSTANCE.createTransition();
											for (State state2 : allStates) {
												if (state2.getName().equals(rule.getRuleConditions().getState())) {
													transition.setTarget(state2);
													state.getOutgoing().add(transition);
												}
											}

										}
									}
								}
							}
						}
					}
					if (rule.getRuleEffect().equals(RuleEffect.BRIDGE)) {

					}
				}
			}
		}

		return modelToTransform;
	}

	/**
	 * @param network
	 * @return
	 */
	public boolean isPolicyEnforcement(Network network) {
		PoliciesEnforcement policiesEnforcement = network.getHasPolicyEnforcement();
		return policiesEnforcement != null;
	}
}