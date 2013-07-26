package com.github.stiepf.inspector.components.internal;

import com.github.stiepf.inspector.components.ComponentDescription;
import com.github.stiepf.inspector.components.ComponentType;

class BeanPredicate extends ComponentPredicate {

  @Override
  boolean matches(ComponentDescription componentDescription) {
    return ComponentType.BEAN.equals(componentDescription.getComponentType());
  }

}
