/*
 * generated by Xtext 2.14.0
 */
package org.atlanmod.cypriot.validation

//import org.atlanmod.cypriot.cyprIoT.Bind

import org.atlanmod.cypriot.cyprIoT.Bind
import org.atlanmod.cypriot.cyprIoT.CyprIoTModel
import org.atlanmod.cypriot.cyprIoT.CyprIoTPackage
import org.atlanmod.cypriot.cyprIoT.InstanceChannel
import org.atlanmod.cypriot.cyprIoT.InstanceThing
import org.atlanmod.cypriot.cyprIoT.Network
import org.atlanmod.cypriot.cyprIoT.Path
import org.atlanmod.cypriot.cyprIoT.Policy
import org.atlanmod.cypriot.cyprIoT.Role
import org.atlanmod.cypriot.cyprIoT.RuleComm
import org.atlanmod.cypriot.cyprIoT.RuleTrigger
import org.atlanmod.cypriot.cyprIoT.TypeChannel
import org.atlanmod.cypriot.cyprIoT.TypeThing
import org.atlanmod.cypriot.cyprIoT.User
import org.atlanmod.cypriot.cyutil.Helpers
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.validation.Check
import org.thingml.xtext.thingML.ExternStatement
import org.thingml.xtext.thingML.Thing

/**
 * This class contains custom validation rules. 
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
class CypriotValidator extends AbstractCypriotValidator {

	// Errors
	public static val ROLE_UNIQUENESS = "Role-Uniqueness"
	public static val USER_UNIQUENESS = "User-Uniqueness"
	public static val INSTANCETHING_UNIQUENESS = "InstanceThing-Uniqueness"
	public static val INSTANCEPUBSUB_UNIQUENESS = "InstancePubSub-Uniqueness"
	public static val INSTANCEPTP_UNIQUENESS = "InstancePTP-Uniqueness"
	public static val NETWORK_UNIQUENESS = "Network-Uniqueness"
	public static val THING_UNIQUENESS = "Thing-Uniqueness"
	public static val PUBSUB_UNIQUENESS = "PubSub-Uniqueness"
	public static val PTP_UNIQUENESS = "PTP-Uniqueness"
	public static val POLICY_UNIQUENESS = "Policy-Uniqueness"
	public static val TOPIC_UNIQUENESS = "Topic-Uniqueness"
	public static val CONNECTIONPOINT_UNIQUENESS = "ConnectionPoint-Uniqueness"
	public static val ONLYONETHING = "OnlyOneThing"
	public static val PORT_CHANNEL__RECEIVE_COMPATIBILITY = "PortChannelReceive-Compatibility"
	public static val PORT_CHANNEL_SEND_COMPATIBILITY = "PortChannelSend-Compatibility"
	public static val PORT_SEND_EXISTANCE = "PortSend-Existence"
	public static val PORT_RECEIVES_EXISTANCE = "PortReceies-Existence"
	public static val DUPLICATE_RULES = "Duplicate-Rules"
	public static val SAME_PATHS_RULEBRIDGE = "SamePaths-RuleBridge"
	public static val CONFLICTING_RULES = "Conflicting-Rules"

	// Warning
	public static val FUNCTION_PARAMETERS = "Function-Parameters"

	public static val WARNING_EMBEDDED = "Warning-Embedded"

	// Info
	public static val IDENTIFY_BIND = "Identify-Bind"

	@Check(FAST)
	def identifyBindForBidge(Bind bind) {
		val network = bind.eContainer as Network
		if (bind.bindAction.literal.equals("<=")) {
			if (bind.name === null) {
				val pathsOfBind = new StringBuilder()
				var i = 0
				for (path : bind.channelToBind.paths) {
					pathsOfBind.append(path.name)
					if(i > 0) pathsOfBind.append(", ")
					i++
				}

				val msg = "Identify this bind to use it in the bridge, syntax : \n bind <ID>:" +
					bind.bindsInstanceThing.name + "." + bind.portToBind.name + " " + bind.bindAction.literal + " " +
					bind.channelToBind.targetedChannelInstance.name + "{" + pathsOfBind + "}";
				info(msg, network, CyprIoTPackage.eINSTANCE.network_HasBinds, network.hasBinds.indexOf(bind),
					IDENTIFY_BIND)
			}
		}
	}

	@Check(FAST)
	def checkBindPortChannelCompatibility(Bind bind) {
		val portBind = bind.portToBind
		val network = bind.eContainer as Network
		val channelToBind = bind.channelToBind

		if (bind.bindAction.literal.equals("=>")) {
			if (portBind.sends.size !== 0) {
				if (!channelToBind.paths.forall[t|t.acceptedMessage.name.equals(portBind.sends.get(0).name)]) {
					val msg = "The port " + portBind.getName() + " is incompatible with at least one topic.";
					error(msg, network, CyprIoTPackage.eINSTANCE.network_HasBinds, network.hasBinds.indexOf(bind),
						PORT_CHANNEL_SEND_COMPATIBILITY)
				}
			} else {
				val msg = "The port " + portBind.getName() + " cannot send a message.";
				error(msg, network, CyprIoTPackage.eINSTANCE.network_HasBinds, network.hasBinds.indexOf(bind),
					PORT_SEND_EXISTANCE)
			}
		} else if (bind.bindAction.literal.equals("<=")) {
			if (portBind.receives.size !== 0) {
				if (!channelToBind.paths.forall[t|t.acceptedMessage.name.equals(portBind.receives.get(0).name)]) {
					val msg = "The port " + portBind.getName() + " is incompatible with at least one topic.";
					error(msg, network, CyprIoTPackage.eINSTANCE.network_HasBinds, network.hasBinds.indexOf(bind),
						PORT_CHANNEL__RECEIVE_COMPATIBILITY)
				}
			} else {
				val msg = "The port " + portBind.getName() + " cannot receive a message.";
				error(msg, network, CyprIoTPackage.eINSTANCE.network_HasBinds, network.hasBinds.indexOf(bind),
					PORT_RECEIVES_EXISTANCE)
			}
		}

	}

	@Check(FAST)
	def checkFunctionNumberOfParameters(RuleTrigger rule) {
		val policy = rule.eContainer as Policy
		val allRules = policy.hasRules.filter [ r |
			if (r instanceof RuleTrigger) {
				val functionName = (r as RuleTrigger).effectTrigger.actionTrigger.thingWithFunction.
					function.name
				val functionInThg = Helpers.allFunctionsThingML(
					(r as RuleTrigger).effectTrigger.actionTrigger.thingWithFunction.thing as TypeThing)
				val functionInTrigger = (r as RuleTrigger).effectTrigger.actionTrigger.thingWithFunction.getFunction.
					parameters.size
				val parameterCountInThg = functionInThg.filter[f|f.name.equals(functionName)].get(0).parameters.size
				r instanceof RuleTrigger &&
					(r as RuleTrigger).effectTrigger.actionTrigger.thingWithFunction.thing instanceof TypeThing &&
					functionInTrigger != parameterCountInThg
			}

		]

		if (allRules.size() > 0) {
			val msg = "The number of parameters in executeFunction does not match with the number of parameters of the function.";
			error(msg, policy, CyprIoTPackage.eINSTANCE.policy_HasRules, policy.hasRules.indexOf(rule),
				FUNCTION_PARAMETERS)
		}
	}

	@Check(FAST)
	def checkDuplicateCommRules(RuleComm rule) {
		val policy = rule.eContainer as Policy
		val allRules = policy.hasRules.filter [ r |
			if (r instanceof RuleComm) {
				val thisRule = r as RuleComm
				val inputRule = rule as RuleComm
				var isAllowThisRule = thisRule.effectComm.allow
				var isAllowInputRule = inputRule.effectComm.allow
				isCommRulesDuplicates(r, rule) && isAllowThisRule == isAllowInputRule
			}

		]

		if (allRules.size() > 1) {
			val rulesstr = new StringBuilder()
			var i = 0
			for (rr : allRules) {
				rulesstr.append(policy.hasRules.indexOf(rr))
				if(i == 0) rulesstr.append(", ")
				i++
			}
			val msg = "Rules at positions " + rulesstr + " are duplicates.";
			error(msg, policy, CyprIoTPackage.eINSTANCE.policy_HasRules, policy.hasRules.indexOf(rule), DUPLICATE_RULES)
		}
	}

	protected def boolean isCommRulesDuplicates(RuleComm r, RuleComm rule) {
		val thisRule = r as RuleComm
		val inputRule = rule as RuleComm
		var nameOfThisSubject = thisRule.commSubject.subjectOther.name
		var nameOfThisObject = thisRule.commObject.objectOther.name
		var actionOfThisRule = thisRule.effectComm.actionComm.literal
		var nameOfInputSubject = inputRule.commSubject.subjectOther.name
		var nameOfInputObject = inputRule.commObject.objectOther.name
		var actionOfInputRule = inputRule.effectComm.actionComm.literal
		r instanceof RuleComm && nameOfThisSubject.equals(nameOfInputSubject) &&
			actionOfThisRule.equals(actionOfInputRule) && nameOfThisSubject.equals(nameOfInputSubject) &&
			nameOfThisObject.equals(nameOfInputObject)
	}

	@Check(FAST)
	def checkConflictingCommRules(RuleComm rule) {
		val policy = rule.eContainer as Policy
		val allRules = policy.hasRules.filter [ r |
			if (r instanceof RuleComm) {
				isCommRulesDuplicates(rule, r)
			}

		]
		if (allRules.size() > 1) {
			val rulesstr = new StringBuilder()
			var i = 0
			for (rr : allRules) {
				rulesstr.append(policy.hasRules.indexOf(rr))
				if(i > 0) rulesstr.append(", ")
				i++
			}
			val msg = "Rules at positions " + rulesstr + " are conflicting.";
			error(msg, policy, CyprIoTPackage.eINSTANCE.policy_HasRules, policy.hasRules.indexOf(rule),
				CONFLICTING_RULES)
		}
	}

	@Check(FAST)
	def checkNumberofThing(TypeThing thing) {
		val thingml = Helpers.getThingInThingML(thing)
		val numberThings = thingml.types.filter[k|k instanceof Thing].size
		val container = thing.eContainer as CyprIoTModel

		if (numberThings != 1) {
			val msg = "The thing " + thing.getName() + " must contain only one thing";
			error(msg, container, CyprIoTPackage.eINSTANCE.cyprIoTModel_DeclareThings,
				container.declareThings.indexOf(thing), ONLYONETHING)
		}

	}

	@Check(FAST)
	def checkInstanceThingUniqueness(InstanceThing instanceThing) {
		val network = instanceThing.eContainer as Network
		val allinstanceThings = network.instantiate.filter(
			k |
				k instanceof InstanceThing && (k as InstanceThing).name == instanceThing.name
		)

		if (allinstanceThings.size() > 1) {
			val msg = "The instance '" + instanceThing.getName() + "' is already declared.";
			error(msg, network, CyprIoTPackage.eINSTANCE.network_Instantiate,
				network.instantiate.indexOf(instanceThing), INSTANCETHING_UNIQUENESS)
		}
	}

	@Check(FAST)
	def checkInstancePubSubUniqueness(InstanceChannel instancePubSub) {
		val network = instancePubSub.eContainer as Network
		val allinstancePubSub = network.instantiate.filter(
			k |
				k instanceof InstanceChannel && (k as InstanceChannel).name == instancePubSub.name
		)

		if (allinstancePubSub.size() > 1) {
			val msg = "The instance '" + instancePubSub.getName() + "' is already declared.";
			error(msg, network, CyprIoTPackage.eINSTANCE.network_Instantiate,
				network.instantiate.indexOf(instancePubSub), INSTANCEPUBSUB_UNIQUENESS)
		}
	}

	@Check(FAST)
	def checkNetworkUniqueness(Network network) {
		val cypriotModel = network.eContainer as CyprIoTModel
		val allNetworks = cypriotModel.specifyNetworks.filter(k|k.name == network.name)

		if (allNetworks.size() > 1) {
			val msg = "The network '" + network.getName() + "' is already declared.";
			error(msg, cypriotModel, CyprIoTPackage.eINSTANCE.cyprIoTModel_SpecifyNetworks,
				cypriotModel.specifyNetworks.indexOf(network), NETWORK_UNIQUENESS)
		}
	}

	@Check(FAST)
	def checkRoleUniqueness(Role role) {
		val cypriotModel = role.eContainer as CyprIoTModel
		val allroles = cypriotModel.declareRoles.filter(k|k.name == role.name)

		if (allroles.size() > 1) {
			val msg = "The role '" + role.getName() + "' is already declared.";
			error(msg, cypriotModel, CyprIoTPackage.eINSTANCE.cyprIoTModel_DeclareRoles,
				cypriotModel.declareRoles.indexOf(role), ROLE_UNIQUENESS)
		}
	}

	@Check(FAST)
	def checkUserUniqueness(User user) {
		val cypriotModel = user.eContainer as CyprIoTModel
		val allUsers = cypriotModel.declareUsers.filter(k|k.name == user.name)

		if (allUsers.size() > 1) {
			val msg = "The user '" + user.getName() + "' is already declared.";
			error(msg, cypriotModel, CyprIoTPackage.eINSTANCE.cyprIoTModel_DeclareUsers,
				cypriotModel.declareUsers.indexOf(user), USER_UNIQUENESS)
		}
	}

	@Check(FAST)
	def checkThingUniqueness(TypeThing thing) {
		val cypriotModel = thing.eContainer as CyprIoTModel
		val allThings = cypriotModel.declareThings.filter(k|k.name == thing.name)

		if (allThings.size() > 1) {
			val msg = "The thing '" + thing.getName() + "' is already declared.";
			error(msg, cypriotModel, CyprIoTPackage.eINSTANCE.cyprIoTModel_DeclareThings,
				cypriotModel.declareThings.indexOf(thing), THING_UNIQUENESS)
		}
	}

	@Check(FAST)
	def checkChannelUniqueness(TypeChannel channel) {
		val cypriotModel = channel.eContainer as CyprIoTModel
		val allPubSubs = cypriotModel.declareChannels.filter(
			k |
				k instanceof TypeChannel && (k as TypeChannel).name == channel.name
		)

		if (allPubSubs.size() > 1) {
			val msg = "The channel  '" + channel.getName() + "' is already declared.";
			error(msg, cypriotModel, CyprIoTPackage.eINSTANCE.cyprIoTModel_DeclareChannels,
				cypriotModel.declareChannels.indexOf(channel), PUBSUB_UNIQUENESS)
		}
	}

	@Check(FAST)
	def checkInstancePathUniqueness(Path path) {
		val channel = path.eContainer as TypeChannel
		val allTopics = channel.hasPaths.filter(k|k.name == path.name)

		if (allTopics.size() > 1) {
			val msg = "The path '" + path.getName() + "' is already declared.";
			error(msg, channel, CyprIoTPackage.eINSTANCE.typeChannel_HasPaths, channel.hasPaths.indexOf(path),
				TOPIC_UNIQUENESS)
		}
	}

	@Check(FAST)
	def checkPolicyUniqueness(Policy policy) {
		val cypriotModel = policy.eContainer as CyprIoTModel
		val allPolicies = cypriotModel.specifyPolicies.filter(k|k.name == policy.name)

		if (allPolicies.size() > 1) {
			val msg = "The policy '" + policy.getName() + "' is already declared.";
			error(msg, cypriotModel, CyprIoTPackage.eINSTANCE.cyprIoTModel_SpecifyPolicies,
				cypriotModel.specifyPolicies.indexOf(policy), POLICY_UNIQUENESS)
		}
	}

	@Check(FAST)
	def checkEmbededCode(InstanceThing instanceThing) {
		val network = instanceThing.eContainer as Network
		val thingmlModel = Helpers.getThingMLFromURI(instanceThing)
		val candidates = EcoreUtil2.getAllContentsOfType(thingmlModel, ExternStatement)
		if (candidates.size > 0) {
			val msg = "The instance '" + instanceThing.getName() +
				"' contains embedded code, make sure it is compatible with the specified platform.";
			warning(msg, network, CyprIoTPackage.eINSTANCE.network_Instantiate,
				network.instantiate.indexOf(instanceThing), WARNING_EMBEDDED)
			return
		}
	}
}
