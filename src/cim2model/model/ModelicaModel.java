package cim2model.model;

import java.util.Map;

/**
 * Only the parameters and their attributes. The model is already created with its equations.
 * @author fragom
 *
 */
public class ModelicaModel {
	
	// TODO: Enumeration for attributes of variables: start, fixed, min, max, nominal
	private String name;
	private Map<String, Float> variables;
	private Map<String, Float> parameters;
	private Map<String, Float> constants;

}
