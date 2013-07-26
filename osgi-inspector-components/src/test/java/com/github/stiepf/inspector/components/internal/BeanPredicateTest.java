package com.github.stiepf.inspector.components.internal;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.stiepf.inspector.components.ComponentDescription;
import com.github.stiepf.inspector.components.ComponentType;

public class BeanPredicateTest {

  private static final String COMPONENT_ID = "component-id";
  
  private ComponentPredicate underTest = new BeanPredicate();
  
  @Test
  public void beanMatches() throws Exception {
    ComponentDescription cd = new ComponentDescription(COMPONENT_ID, ComponentType.BEAN);
    assertTrue(underTest.matches(cd));
  }
  
  @Test
  public void beanMatchesNot() throws Exception {
    ComponentDescription cd = new ComponentDescription(COMPONENT_ID, ComponentType.SERVICE);
    assertFalse(underTest.matches(cd));
  }
}
