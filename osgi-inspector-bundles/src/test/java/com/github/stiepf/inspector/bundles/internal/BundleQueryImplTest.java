package com.github.stiepf.inspector.bundles.internal;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.packageadmin.PackageAdmin;

@RunWith(MockitoJUnitRunner.class)
public class BundleQueryImplTest {

  private BundleQueryImpl underTest;
  
  @Mock
  private BundleContext bundleContextMock;
  
  @Mock
  private ServiceReference serviceReferenceMock;
  
  @Mock
  private PackageAdmin packageAdminMock;
  
  @Mock
  private Bundle bundleMock1;
  
  @Mock
  private Bundle bundleMock2;
  
  
  @Before
  public void setUp() {
    underTest = new BundleQueryImpl(bundleContextMock);
    when(bundleContextMock.getServiceReference(PackageAdmin.class.getName())).thenReturn(serviceReferenceMock);
  }
  
  @Test
  public void initialEmptyPredicates() throws Exception {
    assertEquals(0, underTest.getPredicates().size());
  }
  
  @Test
  public void listIncludesMatchingBundles() throws Exception {
    BundlePredicate predicate = mockPredicate();
    when(predicate.matches(bundleMock1)).thenReturn(true);
    when(predicate.matches(bundleMock2)).thenReturn(true);
    expectList(bundleMock1, bundleMock2);
    List<Bundle> result = underTest.list();
    
    assertEquals(2, result.size());
    assertSame(bundleMock1, result.get(0));
  }
  
  @Test
  public void listExcludesNonMatchingBundles() throws Exception {
    BundlePredicate predicate = mockPredicate();
    when(predicate.matches(bundleMock1)).thenReturn(false);
    when(predicate.matches(bundleMock2)).thenReturn(true);
    expectList(bundleMock1, bundleMock2);
    List<Bundle> result = underTest.list();
    
    assertEquals(1, result.size());
    assertSame(bundleMock2, result.get(0));
  }
  
  @Test
  public void hasEntryMatches() throws Exception {
    String path = "test/foo/bar/baz.xml";
    BundlePredicate hasEntry = hasEntryPredicate(path);
    when(bundleMock1.getEntry(path)).thenReturn(new URL("file://" + path));
    assertTrue(hasEntry.matches(bundleMock1));
  }
  
  @Test
  public void hasEntryMatchesNot() throws Exception {
    String path = "test/foo/bar/baz.xml";
    BundlePredicate hasEntry = hasEntryPredicate(path);
    when(bundleMock1.getEntry(path)).thenReturn(null);
    assertFalse(hasEntry.matches(bundleMock1));
  }
  
  @Test
  public void inStateMatches() throws Exception {
    BundlePredicate inState = inStatePredicate(Bundle.ACTIVE);
    when(bundleMock1.getState()).thenReturn(Bundle.ACTIVE);
    assertTrue(inState.matches(bundleMock1));
  }
  
  @Test
  public void inStateMatchesNot() throws Exception {
    BundlePredicate inState = inStatePredicate(Bundle.ACTIVE);
    when(bundleMock1.getState()).thenReturn(Bundle.INSTALLED);
    assertFalse(inState.matches(bundleMock1));
  }  
  
  private BundlePredicate mockPredicate() {
    BundlePredicate predicate = mock(BundlePredicate.class);
    underTest.getPredicates().add(predicate);
    return predicate;
  }

  private void expectList(Bundle... bundles) {
    when(bundleContextMock.getService(serviceReferenceMock)).thenReturn(packageAdminMock);
    when(bundleContextMock.getBundles()).thenReturn(bundles);
  }
  
  private BundlePredicate hasEntryPredicate(String path) {
    underTest.hasEntry(path);
    return getLastPredicate();
  }
  
  private BundlePredicate inStatePredicate(int state) {
    underTest.inState(state);
    return getLastPredicate();
  }

  private BundlePredicate getLastPredicate() {
    List<BundlePredicate> predicates = underTest.getPredicates();
    return underTest.getPredicates().get(predicates.size() - 1);
  }
}
