package cim2model.mapping;

import cim2model.model.modelica.*;

public interface Mapping 
{
	public void loadMapping(String _xmlSource);
	public ModelicaModel createModel(ModelStereotype _modelKind);
}
