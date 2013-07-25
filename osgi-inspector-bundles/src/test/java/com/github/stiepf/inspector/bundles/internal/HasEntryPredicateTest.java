package com.github.stiepf.inspector.bundles.internal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.Bundle;

@RunWith(MockitoJUnitRunner.class)
public class HasEntryPredicateTest {
  
  private static final String PATH = "test/foo/bar/baz.xml";

  private BundlePredicate underTest = new HasEntryPredicate(PATH);

  @Mock
  private Bundle bundleMock;
  
  @Test
  public void hasEntryMatches() throws Exception {
    when(bundleMock.getEntry(PATH)).thenReturn(new URL("file://" + PATH));
    assertTrue(underTest.matches(bundleMock));
  }
  
  @Test
  public void hasEntryMatchesNot() throws Exception {
    when(bundleMock.getEntry(PATH)).thenReturn(null);
    assertFalse(underTest.matches(bundleMock));
  }
  
  
}
