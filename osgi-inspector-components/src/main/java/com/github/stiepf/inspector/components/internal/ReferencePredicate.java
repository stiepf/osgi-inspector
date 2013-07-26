package com.github.stiepf.inspector.components.internal;

import com.github.stiepf.inspector.components.ComponentDescription;
import com.github.stiepf.inspector.components.ComponentType;

class ReferencePredicate extends ComponentPredicate {

  @Override
  boolean matches(ComponentDescription componentDescription) {
    return ComponentType.REFERENCE.equals(componentDescription.getComponentType());
  }

}
