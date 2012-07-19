/******************************************************************************
 * This file is part of MessageChanger (http://www.spout.org/).               *
 *                                                                            *
 * MessageChanger is licensed under the SpoutDev License Version 1.           *
 *                                                                            *
 * MessageChanger is free software: you can redistribute it and/or modify     *
 * it under the terms of the GNU Lesser General Public License as published by*
 * the Free Software Foundation, either version 3 of the License, or          *
 * (at your option) any later version.                                        *
 *                                                                            *
 * In addition, 180 days after any changes are published, you can use the     *
 * software, incorporating those changes, under the terms of the MIT license, *
 * as described in the SpoutDev License Version 1.                            *
 *                                                                            *
 * MessageChanger is distributed in the hope that it will be useful,          *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 * GNU Lesser General Public License for more details.                        *
 *                                                                            *
 * You should have received a copy of the GNU Lesser General Public License,  *
 * the MIT license and the SpoutDev License Version 1 along with this program.*
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.                                                 *
 ******************************************************************************/

package net.breiden.spout.messagechanger.helper.commands;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Amended class to handle Enums, based on {@link org.spout.api.command.annotated.SimpleInjector}
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
public class EnumSimpleInjector implements EnumInjector {
    private Object[] args;
    private Class<?>[] argClasses;

    public EnumSimpleInjector(Object... args) {
        this.args = args;
        argClasses = new Class[args.length];
        for (int i = 0; i < args.length; ++i) {
            argClasses[i] = args[i].getClass();
        }
    }

    public Object newInstance(Class<?> clazz) {
        try {
            Constructor<?> ctr = null;
            int lowestSubclassCount = Integer.MAX_VALUE;
            for (Constructor<?> c : clazz.getConstructors()) {
                boolean match = true;
                Class<?>[] args = c.getParameterTypes();
                if (args == null || args.length != argClasses.length) {
                    continue;
                }
                int subclassCount = 0;
                for (int i = 0; i < args.length; i++) {
                    if (!args[i].isAssignableFrom(argClasses[i])) {
                        match = false;
                        break;
                    } else {
                        Class<?> a = argClasses[i];
                        while (a != null && !a.equals(args[i])) {
                            subclassCount++;
                            a = a.getSuperclass();
                        }
                    }
                }
                if (match) {
                    if (subclassCount < lowestSubclassCount) {
                        lowestSubclassCount = subclassCount;
                        ctr = c;
                    }
                }
            }
            if (ctr == null) {
                ctr = clazz.getConstructor(argClasses);
            }
            return ctr.newInstance(args);
        } catch (NoSuchMethodException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }
}