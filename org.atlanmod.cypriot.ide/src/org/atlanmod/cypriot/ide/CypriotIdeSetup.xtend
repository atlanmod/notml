/*
 * generated by Xtext 2.14.0
 */
package org.atlanmod.cypriot.ide

import com.google.inject.Guice
import org.atlanmod.cypriot.CypriotRuntimeModule
import org.atlanmod.cypriot.CypriotStandaloneSetup
import org.eclipse.xtext.util.Modules2

/**
 * Initialization support for running Xtext languages as language servers.
 */
class CypriotIdeSetup extends CypriotStandaloneSetup {

	override createInjector() {
		Guice.createInjector(Modules2.mixin(new CypriotRuntimeModule, new CypriotIdeModule))
	}
	
}
