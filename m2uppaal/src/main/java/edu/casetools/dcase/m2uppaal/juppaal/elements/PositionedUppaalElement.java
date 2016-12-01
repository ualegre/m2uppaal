/*
 * Copyright 2015 @author Thomas Bogholm, Kasper Luckow and Unai Alegre
 * 
 * This file is part of DCASE (Design for Context-Aware Systems Engineering), a module 
 * of Modelio that aids the design of a Context-Aware System (C-AS). 
 * 
 * DCASE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DCASE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DCASE.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.casetools.dcase.m2uppaal.juppaal.elements;

import org.jdom2.Element;

public abstract class PositionedUppaalElement extends UppaalElement {
    private int posX;
    private int posY;
    private boolean positioned = false;

    /**
     * The position of an element
     * 
     * @param x
     *            The x position
     * @param y
     *            The y position
     */
    public PositionedUppaalElement(int x, int y) {
	this.posX = x;
	this.posY = y;
	setPositioned(true);
    }

    public PositionedUppaalElement(Element xmlRepresentation) {
	if (xmlRepresentation.getAttributeValue("x") == null || xmlRepresentation.getAttributeValue("y") == null) {
	    positioned = false;
	} else {
	    posX = Integer.parseInt(xmlRepresentation.getAttributeValue("x"));
	    posY = Integer.parseInt(xmlRepresentation.getAttributeValue("y"));
	    setPositioned(true);
	}
    }

    public boolean isPositioned() {
	return positioned;
    }

    public void setPositioned(boolean positioned) {
	this.positioned = positioned;
    }

    public int getPosX() {
	return posX;
    }

    public void setPosX(int posX) {
	this.posX = posX;
	positioned = true;
    }

    public int getPosY() {
	return posY;
    }

    public void setPosY(int posY) {
	this.posY = posY;
	positioned = true;
    }

    /**
     * Creates an XML Element object corresponding to the Nail object
     * 
     * @return XML Element
     */
    @Override
    protected Element generateXMLElement() {
	Element result = super.generateXMLElement();
	if (isPositioned()) {
	    result.setAttribute("x", Integer.toString(posX));
	    result.setAttribute("y", Integer.toString(posY));
	}

	return result;
    }

}
