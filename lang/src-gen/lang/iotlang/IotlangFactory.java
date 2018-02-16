/**
 * generated by Xtext 2.13.0
 */
package lang.iotlang;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see lang.iotlang.IotlangPackage
 * @generated
 */
public interface IotlangFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  IotlangFactory eINSTANCE = lang.iotlang.impl.IotlangFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Io TLang Model</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Io TLang Model</em>'.
   * @generated
   */
  IoTLangModel createIoTLangModel();

  /**
   * Returns a new object of class '<em>Platform Annotation</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Platform Annotation</em>'.
   * @generated
   */
  PlatformAnnotation createPlatformAnnotation();

  /**
   * Returns a new object of class '<em>Thing</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Thing</em>'.
   * @generated
   */
  Thing createThing();

  /**
   * Returns a new object of class '<em>Channel</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Channel</em>'.
   * @generated
   */
  Channel createChannel();

  /**
   * Returns a new object of class '<em>Pub Sub</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Pub Sub</em>'.
   * @generated
   */
  PubSub createPubSub();

  /**
   * Returns a new object of class '<em>Point To Point</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Point To Point</em>'.
   * @generated
   */
  PointToPoint createPointToPoint();

  /**
   * Returns a new object of class '<em>Policy</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Policy</em>'.
   * @generated
   */
  Policy createPolicy();

  /**
   * Returns a new object of class '<em>Port</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Port</em>'.
   * @generated
   */
  Port createPort();

  /**
   * Returns a new object of class '<em>Protocol</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Protocol</em>'.
   * @generated
   */
  Protocol createProtocol();

  /**
   * Returns a new object of class '<em>Message</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Message</em>'.
   * @generated
   */
  Message createMessage();

  /**
   * Returns a new object of class '<em>Topic</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Topic</em>'.
   * @generated
   */
  Topic createTopic();

  /**
   * Returns a new object of class '<em>Rule</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Rule</em>'.
   * @generated
   */
  Rule createRule();

  /**
   * Returns a new object of class '<em>Domain</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Domain</em>'.
   * @generated
   */
  Domain createDomain();

  /**
   * Returns a new object of class '<em>Instance Thing</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Instance Thing</em>'.
   * @generated
   */
  InstanceThing createInstanceThing();

  /**
   * Returns a new object of class '<em>Instance Pub Sub</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Instance Pub Sub</em>'.
   * @generated
   */
  InstancePubSub createInstancePubSub();

  /**
   * Returns a new object of class '<em>Instance Pt P</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Instance Pt P</em>'.
   * @generated
   */
  InstancePtP createInstancePtP();

  /**
   * Returns a new object of class '<em>Instance Channel</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Instance Channel</em>'.
   * @generated
   */
  InstanceChannel createInstanceChannel();

  /**
   * Returns a new object of class '<em>Instance Policy</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Instance Policy</em>'.
   * @generated
   */
  InstancePolicy createInstancePolicy();

  /**
   * Returns a new object of class '<em>Network Configuration</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Network Configuration</em>'.
   * @generated
   */
  NetworkConfiguration createNetworkConfiguration();

  /**
   * Returns a new object of class '<em>Bind</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Bind</em>'.
   * @generated
   */
  Bind createBind();

  /**
   * Returns a new object of class '<em>Connect</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Connect</em>'.
   * @generated
   */
  Connect createConnect();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  IotlangPackage getIotlangPackage();

} //IotlangFactory
