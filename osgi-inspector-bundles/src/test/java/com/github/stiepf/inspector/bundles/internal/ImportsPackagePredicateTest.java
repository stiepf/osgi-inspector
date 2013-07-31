package com.github.stiepf.inspector.bundles.internal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.Bundle;
import org.osgi.service.packageadmin.ExportedPackage;
import org.osgi.service.packageadmin.PackageAdmin;

@RunWith(MockitoJUnitRunner.class)
public class ImportsPackagePredicateTest extends PackageAdminProvider {

  private static final String PACKAGE_NAME = "com.github.stiepf.inspector.bundles";

  private BundlePredicate underTest;
  
  @Mock
  private PackageAdmin packageAdminMock;
  
  @Mock
  private Bundle bundleMock;

  @Mock
  private ExportedPackage exportedPackageMock;
  
  @Before
  public void setUp() {
    underTest = new ImportsPackagePredicate(this, PACKAGE_NAME);
  }
  
  @Test
  public void importsPackageMatches() throws Exception {
    when(packageAdminMock.getExportedPackages(PACKAGE_NAME)).thenReturn(new ExportedPackage[] {exportedPackageMock});
    when(exportedPackageMock.getImportingBundles()).thenReturn(new Bundle[] {bundleMock});
    assertTrue(underTest.matches(bundleMock));
  }

  @Test
  public void importsPackageMatchesNot() throws Exception {
    when(packageAdminMock.getExportedPackages(PACKAGE_NAME)).thenReturn(new ExportedPackage[] {exportedPackageMock});
    when(exportedPackageMock.getImportingBundles()).thenReturn(new Bundle[] {});
    assertFalse(underTest.matches(bundleMock));
  }
  
  @Override
  PackageAdmin getPackageAdmin() {
    return packageAdminMock;
  }
}
