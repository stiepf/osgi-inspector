package com.github.stiepf.inspector.components.internal;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.stiepf.inspector.components.ComponentDescription;
import com.github.stiepf.inspector.components.ComponentType;

public class TypePredicateTest {
  
  private static final String COMPONENT_ID = "component-id";
  private static final String CLASS_NAME = "com.github.stiepf.inspector.components.ComponentDescription";
  private static final String OTHER = "com.github.stiepf.inspector.components.ContainerDescription";
  
  private ComponentPredicate underTest = new TypePredicate(CLASS_NAME);
  
  @Test
  public void typeMatches() throws Exception {
    ComponentDescription cd = new ComponentDescription(COMPONENT_ID, ComponentType.BEAN);
    cd.addClassName(CLASS_NAME);
    cd.addClassName(OTHER);
    assertTrue(underTest.matches(cd));
  }
  
  @Test
  public void typeMatchesNotWithOther() throws Exception {
    ComponentDescription cd = new ComponentDescription(COMPONENT_ID, ComponentType.BEAN);
    cd.addClassName(OTHER);
    assertFalse(underTest.matches(cd));
  }
  
  @Test
  public void componentIdMatchesNotWithNull() throws Exception {
    ComponentDescription cd = new ComponentDescription(COMPONENT_ID, ComponentType.BEAN);
    assertFalse(underTest.matches(cd));
  }
}
