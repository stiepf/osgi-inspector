package com.github.stiepf.inspector.components.internal;

import com.github.stiepf.inspector.components.ComponentDescription;

class TypePredicate extends ComponentPredicate {

  private final String className;
  
  TypePredicate(String className) {
    this.className = className;
  }

  @Override
  boolean matches(ComponentDescription componentDescription) {
    return componentDescription.getClassNames().contains(className);
  }

}
