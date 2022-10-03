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

public class GroupRef {

    private final SeasonRef seasonRef;
    private final GroupTypeRef groupTypeRef;

    private GroupRef(SeasonRef seasonRef, GroupTypeRef groupTypeRef) {
        this.seasonRef = seasonRef;
        this.groupTypeRef = groupTypeRef;
    }

    public static GroupRef of(SeasonRef seasonRef, GroupTypeRef groupTypeRef) {
        Objects.requireNonNull(seasonRef);
        Objects.requireNonNull(groupTypeRef);
        return new GroupRef(seasonRef, groupTypeRef);
    }

    public SeasonRef season() {
        return seasonRef;
    }

    public GroupTypeRef groupType() {
        return groupTypeRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupRef groupRef = (GroupRef) o;
        return Objects.equals(seasonRef, groupRef.seasonRef)
                && Objects.equals(groupTypeRef, groupRef.groupTypeRef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seasonRef, groupTypeRef);
    }

}
