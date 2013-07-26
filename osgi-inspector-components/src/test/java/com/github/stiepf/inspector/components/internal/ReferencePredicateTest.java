package com.github.stiepf.inspector.components.internal;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.stiepf.inspector.components.ComponentDescription;
import com.github.stiepf.inspector.components.ComponentType;

public class ReferencePredicateTest {

  private static final String COMPONENT_ID = "component-id";
  
  private ComponentPredicate underTest = new ReferencePredicate();
  
  @Test
  public void referenceMatches() throws Exception {
    ComponentDescription cd = new ComponentDescription(COMPONENT_ID, ComponentType.REFERENCE);
    assertTrue(underTest.matches(cd));
  }
  
  @Test
  public void referenceMatchesNot() throws Exception {
    ComponentDescription cd = new ComponentDescription(COMPONENT_ID, ComponentType.BEAN);
    assertFalse(underTest.matches(cd));
  }
}
