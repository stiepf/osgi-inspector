package com.github.stiepf.inspector.bundles.internal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.Bundle;

@RunWith(MockitoJUnitRunner.class)
public class HasHeaderPredicateTest {
  
  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";

  private BundlePredicate underTest = new HasHeaderPredicate(KEY, VALUE);

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private Bundle bundleMock;
  
  @Test
  public void hasHeaderMatches() throws Exception {
    when(bundleMock.getHeaders().get(KEY)).thenReturn(VALUE);
    assertTrue(underTest.matches(bundleMock));
  }
  
  @Test
  public void hasHeaderMatchesNot() throws Exception {
    when(bundleMock.getHeaders().get(KEY)).thenReturn(null);
    assertFalse(underTest.matches(bundleMock));
  }
  
  
}
