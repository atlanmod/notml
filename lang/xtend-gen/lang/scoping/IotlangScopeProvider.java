/**
 * generated by Xtext 2.13.0
 */
package lang.scoping;

import com.google.common.base.Objects;
import java.util.ArrayList;
import lang.iotlang.IotlangPackage;
import lang.scoping.AbstractIotlangScopeProvider;
import lang.util.Helpers;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
@SuppressWarnings("all")
public class IotlangScopeProvider extends AbstractIotlangScopeProvider {
  private final IotlangPackage iotlangInstance = IotlangPackage.eINSTANCE;
  
  protected ArrayList EMPTY = new ArrayList<Object>();
  
  @Override
  public IScope getScope(final EObject context, final EReference reference) {
    EReference _rule_Subject = this.iotlangInstance.getRule_Subject();
    boolean _equals = Objects.equal(reference, _rule_Subject);
    if (_equals) {
      return Scopes.scopeFor(Helpers.allThings(Helpers.findContainingModel(context)));
    } else {
      EReference _rule_Object = this.iotlangInstance.getRule_Object();
      boolean _equals_1 = Objects.equal(reference, _rule_Object);
      if (_equals_1) {
        return Scopes.scopeFor(Helpers.allThings(Helpers.findContainingModel(context)));
      } else {
        EReference _instanceThing_TypeThing = this.iotlangInstance.getInstanceThing_TypeThing();
        boolean _equals_2 = Objects.equal(reference, _instanceThing_TypeThing);
        if (_equals_2) {
          return Scopes.scopeFor(Helpers.allThings(Helpers.findContainingModel(context)));
        } else {
          EReference _instancePolicy_TypePolicy = this.iotlangInstance.getInstancePolicy_TypePolicy();
          boolean _equals_3 = Objects.equal(reference, _instancePolicy_TypePolicy);
          if (_equals_3) {
            return Scopes.scopeFor(Helpers.allPolicies(Helpers.findContainingModel(context)));
          } else {
            EReference _networkConfiguration_Enforces = this.iotlangInstance.getNetworkConfiguration_Enforces();
            boolean _equals_4 = Objects.equal(reference, _networkConfiguration_Enforces);
            if (_equals_4) {
              return Scopes.scopeFor(Helpers.allConfigs(Helpers.findContainingModel(context)).get(0).getInstancePoliciy());
            } else {
              EReference _bind_ThingInstance = this.iotlangInstance.getBind_ThingInstance();
              boolean _equals_5 = Objects.equal(reference, _bind_ThingInstance);
              if (_equals_5) {
                return Scopes.scopeFor(Helpers.allConfigs(Helpers.findContainingModel(context)).get(0).getThingInstances());
              } else {
                EReference _bind_PubSubInstance = this.iotlangInstance.getBind_PubSubInstance();
                boolean _equals_6 = Objects.equal(reference, _bind_PubSubInstance);
                if (_equals_6) {
                  return Scopes.scopeFor(Helpers.allPubSubinstances(Helpers.findContainingModel(context)));
                } else {
                  EReference _bind_Topics = this.iotlangInstance.getBind_Topics();
                  boolean _equals_7 = Objects.equal(reference, _bind_Topics);
                  if (_equals_7) {
                    return Scopes.scopeFor(Helpers.allTopics(Helpers.findContainingModel(context)));
                  } else {
                    EReference _topic_AcceptedMessages = this.iotlangInstance.getTopic_AcceptedMessages();
                    boolean _equals_8 = Objects.equal(reference, _topic_AcceptedMessages);
                    if (_equals_8) {
                      return Scopes.scopeFor(Helpers.allMessages(Helpers.findContainingModel(context)));
                    } else {
                      EReference _bind_PubSubInstance_1 = this.iotlangInstance.getBind_PubSubInstance();
                      boolean _equals_9 = Objects.equal(reference, _bind_PubSubInstance_1);
                      if (_equals_9) {
                        return Scopes.scopeFor(Helpers.allPubSubinstances(Helpers.findContainingModel(context)));
                      } else {
                        EReference _instancePubSub_TypeChannel = this.iotlangInstance.getInstancePubSub_TypeChannel();
                        boolean _equals_10 = Objects.equal(reference, _instancePubSub_TypeChannel);
                        if (_equals_10) {
                          return Scopes.scopeFor(Helpers.allPusSub(Helpers.findContainingModel(context)));
                        } else {
                          EReference _instancePubSub_OverProtocol = this.iotlangInstance.getInstancePubSub_OverProtocol();
                          boolean _equals_11 = Objects.equal(reference, _instancePubSub_OverProtocol);
                          if (_equals_11) {
                            return Scopes.scopeFor(Helpers.allProtocol(Helpers.findContainingModel(context)));
                          } else {
                            EReference _instancePtP_TypeChannel = this.iotlangInstance.getInstancePtP_TypeChannel();
                            boolean _equals_12 = Objects.equal(reference, _instancePtP_TypeChannel);
                            if (_equals_12) {
                              return Scopes.scopeFor(Helpers.allPTP(Helpers.findContainingModel(context)));
                            } else {
                              EReference _instancePtP_OverProtocol = this.iotlangInstance.getInstancePtP_OverProtocol();
                              boolean _equals_13 = Objects.equal(reference, _instancePtP_OverProtocol);
                              if (_equals_13) {
                                return Scopes.scopeFor(Helpers.allProtocol(Helpers.findContainingModel(context)));
                              } else {
                                EReference _connect_ThingInstance = this.iotlangInstance.getConnect_ThingInstance();
                                boolean _equals_14 = Objects.equal(reference, _connect_ThingInstance);
                                if (_equals_14) {
                                  return Scopes.scopeFor(Helpers.allConfigs(Helpers.findContainingModel(context)).get(0).getThingInstances());
                                } else {
                                  EReference _connect_PtpInstance = this.iotlangInstance.getConnect_PtpInstance();
                                  boolean _equals_15 = Objects.equal(reference, _connect_PtpInstance);
                                  if (_equals_15) {
                                    return Scopes.scopeFor(Helpers.allPtpinstances(Helpers.findContainingModel(context)));
                                  } else {
                                    EReference _rule_Ports = this.iotlangInstance.getRule_Ports();
                                    boolean _equals_16 = Objects.equal(reference, _rule_Ports);
                                    if (_equals_16) {
                                      return Scopes.scopeFor(Helpers.allPorts(Helpers.findContainingModel(context)));
                                    } else {
                                      EReference _rule_ObjectMessage = this.iotlangInstance.getRule_ObjectMessage();
                                      boolean _equals_17 = Objects.equal(reference, _rule_ObjectMessage);
                                      if (_equals_17) {
                                        return Scopes.scopeFor(Helpers.allMessages(Helpers.findContainingModel(context)));
                                      } else {
                                        String _name = reference.getName();
                                        String _plus = ("INFO: Resolving reference : " + _name);
                                        String _plus_1 = (_plus + " in Class ");
                                        EObject _eContainer = reference.eContainer();
                                        String _name_1 = ((ENamedElement) _eContainer).getName();
                                        String _plus_2 = (_plus_1 + _name_1);
                                        System.err.println(_plus_2);
                                      }
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return Scopes.scopeFor(this.EMPTY);
  }
}
