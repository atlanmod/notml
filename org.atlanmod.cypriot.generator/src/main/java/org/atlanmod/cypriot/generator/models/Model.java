package org.atlanmod.cypriot.generator.models;

import java.io.File;

import org.apache.log4j.Logger;
import org.atlanmod.cypriot.generator.commons.Utilities;
import org.atlanmod.cypriot.generator.exceptions.ModelNotFoundException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

public abstract class Model {
	
	public Model() {
		this.loadModel();
	}
	
	abstract void loadModel();
	abstract void registerFactory();
	
	/**
	 * Load the EMF graph of the model from a File
	 * 
	 * @param file
	 * @return
	 * @throws ModelNotFoundException 
	 */
	public <T extends EObject> T loadModelFromFile(File file, Class<T> type, Logger log) throws ModelNotFoundException {
		registerFactory();
		Resource model = Utilities.createResourceFromFile(file, log);
		
		try {
			model.load(null);
			EcoreUtil.resolveAll(model);
			for (Resource r : model.getResourceSet().getResources()) {
				if(!Utilities.checkErrorsInModel(r, log)) {
					throw new Exception();
				}
			}
			;
			return type.cast(model.getContents().get(0));

		} catch (Exception e) {
			throw new ModelNotFoundException("The model couldn't be loaded");
		}
	}
}
