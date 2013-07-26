package com.github.stiepf.inspector.components.internal;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.stiepf.inspector.components.ComponentDescription;
import com.github.stiepf.inspector.components.ComponentType;

public class ComponentIdPredicateTest {

  private static final String COMPONENT_ID = "component-id";
  private static final String OTHER = "other-id";
  
  private ComponentPredicate underTest = new ComponentIdPredicate(COMPONENT_ID);
  
  @Test
  public void componentIdMatches() throws Exception {
    ComponentDescription cd = new ComponentDescription(COMPONENT_ID, ComponentType.BEAN);
    assertTrue(underTest.matches(cd));
  }
  
  @Test
  public void componentIdMatchesNotWithOther() throws Exception {
    ComponentDescription cd = new ComponentDescription(OTHER, ComponentType.BEAN);
    assertFalse(underTest.matches(cd));
  }
  
  @Test
  public void componentIdMatchesNotWithNull() throws Exception {
    ComponentDescription cd = new ComponentDescription(null, ComponentType.BEAN);
    assertFalse(underTest.matches(cd));
  }
}
