package com.github.stiepf.inspector.bundles.internal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.Bundle;

@RunWith(MockitoJUnitRunner.class)
public class InStatePredicateTest {
  
  private BundlePredicate underTest = new InStatePredicate(Bundle.ACTIVE);

  @Mock
  private Bundle bundleMock;
  
  @Test
  public void inStateMatches() throws Exception {
    when(bundleMock.getState()).thenReturn(Bundle.ACTIVE);
    assertTrue(underTest.matches(bundleMock));
  }
  
  @Test
  public void inStateMatchesNot() throws Exception {
    when(bundleMock.getState()).thenReturn(Bundle.INSTALLED);
    assertFalse(underTest.matches(bundleMock));
  }
  
  
}
