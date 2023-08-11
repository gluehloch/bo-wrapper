/*
 * ============================================================================
 * Project betoffice-wrapper Copyright (c) 2000-2022 by Andre Winkler. All
 * rights reserved.
 * ============================================================================
 * GNU GENERAL PUBLIC LICENSE TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND
 * MODIFICATION
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */

package de.betoffice.wrapper.api;

import java.util.Objects;

public class RoundIndex {

    private final int index;

    private RoundIndex(int index) {
        this.index = index;
    }

    public static RoundIndex of(int index) {
        if (index < 1) {
            throw new IndexOutOfBoundsException("index is smaller than 1");
        }

        return new RoundIndex(index);
    }

    public int index() {
        return index;
    }

    /**
     * Spieltage werden von 1..N gezählt. Betoffice intern sind die Spieltag von 0..N-1 nummeriert.
     *
     * @return index() - 1
     */
    public int betofficeIndex() {
        return index() - 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoundIndex that = (RoundIndex) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
    
    @Override
    public String toString() {
        return String.format("RoundRefRef=[%d]", this.index);
    }

}
