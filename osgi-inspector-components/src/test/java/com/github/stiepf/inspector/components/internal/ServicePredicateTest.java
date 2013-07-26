package com.github.stiepf.inspector.components.internal;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.stiepf.inspector.components.ComponentDescription;
import com.github.stiepf.inspector.components.ComponentType;

public class ServicePredicateTest {

  private static final String COMPONENT_ID = "component-id";
  
  private ComponentPredicate underTest = new ServicePredicate();
  
  @Test
  public void serviceMatches() throws Exception {
    ComponentDescription cd = new ComponentDescription(COMPONENT_ID, ComponentType.SERVICE);
    assertTrue(underTest.matches(cd));
  }
  
  @Test
  public void serviceMatchesNot() throws Exception {
    ComponentDescription cd = new ComponentDescription(COMPONENT_ID, ComponentType.REFERENCE);
    assertFalse(underTest.matches(cd));
  }
}
