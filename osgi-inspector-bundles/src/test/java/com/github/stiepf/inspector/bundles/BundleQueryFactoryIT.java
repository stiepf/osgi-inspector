package com.github.stiepf.inspector.bundles;

import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.osgi.framework.Bundle;


@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class BundleQueryFactoryIT {

  @Inject
  private BundleQueryFactory underTest;
  
  @Configuration
  public Option[] configure() {
    return options(
        mavenBundle("com.github.stiepf.inspector", "osgi-inspector-bundles", "0.1.1-SNAPSHOT"),
        junitBundles()
    );
  }
  
  @Test
  public void bundleQueryFactoryAvailable() throws Exception {
    assertNotNull(underTest);
  }
  
  @Test
  public void returnsBundleQuery() throws Exception {
    Object result = underTest.createBundleQuery();
    assertNotNull(result);
    assert(result instanceof BundleQuery);
  }
  
  @Test
  public void executesQuery() throws Exception {
    BundleQuery query = underTest.createBundleQuery();
    query.providesService(BundleQueryFactory.class.getName());
    List<Bundle> result = query.list();
    assertEquals(1, result.size());
    assertEquals("com.github.stiepf.inspector.osgi-inspector-bundles", result.get(0).getSymbolicName());
  }
}
