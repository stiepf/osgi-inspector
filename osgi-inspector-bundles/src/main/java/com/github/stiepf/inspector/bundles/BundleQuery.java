/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.stiepf.inspector.bundles;

import java.util.List;

import org.osgi.framework.Bundle;

/**
 * Represents the means to build a query by adding criteria and run it against an OSGi framework implementation.
 * Running the execute() method must return a valid result at any time, i.e. the result will include all known bundles, 
 * if no limiting criteria are active or an empty list, if no bundle matches the given criteria. Every bundle in the 
 * result must match all criteria.
 * 
 * @author stiepf@googlemail.com
 *
 */
public interface BundleQuery {

  /**
   * Limits the result of execute() to bundles importing the given package.
   * 
   * @param packageName
   *  the full qualified package name
   * @return
   *  the query object itself
   */
  BundleQuery importsPackage(String packageName);

  /**
   * Limits the result of execute() to bundles exporting the given package.
   * 
   * @param packageName
   *  the full qualified package name
   * @return
   *  the query object itself
   */
  BundleQuery exportsPackage(String packageName);

  
  /**
   * Limits the result of execute() to bundles providing a service with the given interface.
   * 
   * @param serviceInterface
   *  the full qualified class name of the service interface
   * @return
   *  the query object itself
   */
  BundleQuery providesService(String serviceInterface);

  /**
   * Limits the result of execute() to bundles consuming a service with the given interface.
   * 
   * @param serviceInterface
   *  the full qualified class name of the service interface
   * @return
   *  the query object itself
   */  
  BundleQuery consumesService(String serviceInterface);

  /**
   * Limits the result of execute() to bundles the given header value pair in their OSGi manifest.
   * 
   * @param key
   *  the manifest header's name
   * @param value
   *  the manifest header's value 
   * @return
   *  the query object itself
   */
  BundleQuery hasHeader(String key, String value);

  /**
   * Limits the result of execute() to bundles providing the given resource.
   * 
   * @param path
   *  the path to the resource, usinf '/' as path separator
   * @return
   */
  BundleQuery hasEntry(String path);

  /**
   * Limits the result of execute() to bundles in the given state.
   * 
   * @param bundleState
   *  the bundle state as defined in org.osgi.framework.Bundle
   * @return
   *  the query object itself
   */
  BundleQuery inState(int bundleState);

  /**
   * Returns a list of all bundles matching the previously added criteria. You may call execute() repeatedly and add 
   * further criteria between the consecutive calls.
   * 
   * @return
   *  all bundles matching the given criteria
   */
  List<Bundle> execute();
}
