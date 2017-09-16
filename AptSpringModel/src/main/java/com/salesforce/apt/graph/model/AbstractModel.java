/*
 * Copyright © 2017, Saleforce.com, Inc
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.salesforce.apt.graph.model;

import java.util.Optional;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public abstract class AbstractModel {

  private final String elementLocation;
  
  private transient Optional<Element> sourceElement;
  
  public Optional<Element> getSourceElement() {
    if (sourceElement == null) {
      sourceElement = Optional.empty();
    }
    return sourceElement;
  }

  public AbstractModel(String elementLocation) {
    super();
    this.elementLocation = elementLocation;
    this.sourceElement = Optional.empty();
  }

  public AbstractModel(Element sourceElement) {
    super();
    this.elementLocation = getElementLocation(sourceElement);
    this.sourceElement = Optional.of(sourceElement);
  }

  private String getElementLocation(Element element) {
    if (element.getKind() == ElementKind.CLASS) {
      return element.toString();
    }
    Element source = element;
    while (source.getKind() != ElementKind.CLASS) {
      source = source.getEnclosingElement();
    }
    return source.toString() + "." + element.getSimpleName() + (element.getKind() == ElementKind.METHOD ? "(...)" : "");
  }
  
  public String getElementLocation() {
    return elementLocation;
  }

  public abstract String getIdentity();
  
}
