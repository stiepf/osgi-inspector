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
package com.github.stiepf.inspector.components;

import org.apache.aries.blueprint.PassThroughMetadata;
import org.osgi.service.blueprint.reflect.BeanMetadata;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.ServiceMetadata;
import org.osgi.service.blueprint.reflect.ServiceReferenceMetadata;

public enum ComponentType {
  UNKNOWN, BEAN, SERVICE, REFERENCE, IMPLICIT;

  public static ComponentType getComponentType(ComponentMetadata metadata) {
    if (metadata instanceof BeanMetadata) {
      return BEAN;
    } else if (metadata instanceof ServiceMetadata) {
      return SERVICE;
    } else if (metadata instanceof ServiceReferenceMetadata) {
      return REFERENCE;
    } else if (metadata instanceof PassThroughMetadata) {
      return IMPLICIT;
    }
    return UNKNOWN;
  }
}
