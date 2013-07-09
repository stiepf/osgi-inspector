package com.github.stiepf.inspector.bundles.internal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.BundleContext;
import org.osgi.service.packageadmin.PackageAdmin;

import com.github.stiepf.inspector.bundles.BundleQuery;
import com.github.stiepf.inspector.bundles.BundleQueryFactory;

@RunWith(MockitoJUnitRunner.class)
public class BundleQueryFactoryImplTest {

  private BundleQueryFactory underTest;
  
  @Mock
  private BundleContext bundleContextMock;
  
  @Before
  public void setUp() {
    underTest = new BundleQueryFactoryImpl(bundleContextMock);
  }
  
  @Test
  public void bundleQueryCreation() throws Exception {
    BundleQuery result = underTest.createBundleQuery();
    assertTrue(result instanceof BundleQueryImpl);
    verify(bundleContextMock).getServiceReference(PackageAdmin.class.getName());
  }
  
}
