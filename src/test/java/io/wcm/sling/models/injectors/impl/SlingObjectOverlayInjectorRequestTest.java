/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2014 wcm.io
 * %%
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
 * #L%
 */
package io.wcm.sling.models.injectors.impl;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;
import io.wcm.sling.commons.request.RequestContext;

import java.lang.reflect.AnnotatedElement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.scripting.SlingBindings;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableMap;

@RunWith(MockitoJUnitRunner.class)
public class SlingObjectOverlayInjectorRequestTest {

  @Rule
  public SlingContext context = new SlingContext(ResourceResolverType.RESOURCERESOLVER_MOCK);

  @Mock
  protected AnnotatedElement annotatedElement;
  @Mock
  protected SlingHttpServletRequest request;
  @Mock
  protected SlingHttpServletResponse response;
  @Mock
  protected SlingScriptHelper scriptHelper;
  @Mock
  protected ResourceResolver resourceResolver;
  @Mock
  protected Resource resource;
  @Mock
  protected RequestContext requestContext;

  protected SlingObjectOverlayInjector injector;

  @Before
  public void setUp() {
    context.registerService(RequestContext.class, requestContext);
    context.registerInjectActivateService(new ModelsImplConfiguration(),
        ImmutableMap.<String, Object>of(ModelsImplConfiguration.PARAM_REQUEST_THREAD_LOCAL,
            ModelsImplConfiguration.PARAM_REQUEST_THREAD_LOCAL_DEFAULT));

    injector = context.registerInjectActivateService(new SlingObjectOverlayInjector());

    SlingBindings bindings = new SlingBindings();
    bindings.put(SlingBindings.SLING, this.scriptHelper);
    when(this.resource.getResourceResolver()).thenReturn(this.resourceResolver);
    when(this.request.getResourceResolver()).thenReturn(this.resourceResolver);
    when(this.request.getResource()).thenReturn(this.resource);
    when(this.request.getAttribute(SlingBindings.class.getName())).thenReturn(bindings);
    when(this.scriptHelper.getResponse()).thenReturn(this.response);
  }

  protected Object adaptable() {
    return this.request;
  }

  @Test
  public void testResourceResolver() {
    Object result = this.injector.getValue(adaptable(), null, ResourceResolver.class, this.annotatedElement, null);
    assertSame(this.resourceResolver, result);
  }

  @Test
  public void testResource() {
    Object result = this.injector.getValue(adaptable(), null, Resource.class, this.annotatedElement, null);
    assertNull(result);

    when(annotatedElement.isAnnotationPresent(SlingObject.class)).thenReturn(true);
    result = this.injector.getValue(adaptable(), null, Resource.class, this.annotatedElement, null);
    assertSame(resource, result);
  }

  @Test
  public void testRequest() {
    Object result = this.injector.getValue(adaptable(), null, SlingHttpServletRequest.class,
        this.annotatedElement, null);
    assertSame(this.request, result);

    result = this.injector.getValue(adaptable(), null, HttpServletRequest.class, this.annotatedElement, null);
    assertSame(this.request, result);
  }

  @Test
  public void testResponse() {
    Object result = this.injector.getValue(adaptable(), null, SlingHttpServletResponse.class, this.annotatedElement, null);
    assertSame(this.response, result);

    result = this.injector.getValue(adaptable(), null, HttpServletResponse.class, this.annotatedElement, null);
    assertSame(this.response, result);
  }

  @Test
  public void testScriptHelper() {
    Object result = this.injector.getValue(adaptable(), null, SlingScriptHelper.class, this.annotatedElement, null);
    assertSame(this.scriptHelper, result);
  }

  @Test
  public void testInvalid() {
    Object result = this.injector.getValue(new StringBuffer(), null, SlingScriptHelper.class, this.annotatedElement, null);
    assertNull(result);
  }

}
