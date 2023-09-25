# Eclipse E-Attribute
## Description
E-Attributes are what eclipse uses to represent the parameters of a launch configuration. They are nothing more than a key->value mapping with a special XML syntax, and are represented in java as a `TreeMap<String, Object>`.

Each E-Attribute infers its Java type from its XML element name, and supplies a `key` XML attribute which will be used in the `TreeMap`. The location of the E-Attribute's value depends on the type.

The information in this documentd was sourced from [here](https://github.com/eclipse-platform/eclipse.platform/blob/master/debug/org.eclipse.debug.core/core/org/eclipse/debug/internal/core/LaunchConfigurationInfo.java).

## E-Attribute Types
### Groups
E-Attributes can be grouped into two categories, depending on how their value is stored. The first category is **Primitive E-Attributes**, representing strings, booleans, and integers. Primitive types always store their value in the XML attribute `value` within their XML element.

The remaining types are **Collection E-Attributes**, composed of sets, maps, and lists. These types store their values in sub-elements, which are specific "collection entry" E-Attributes that can only be used within the relevant collection.

### Type Names
The list of valid E-Attribute type names is as follows:
* `stringAttribute`
* `intAttribute`
* `booleanAttribute`
* `setAttribute`
  * `setEntry`
* `mapAttribute`
  * `mapEntry`
* `listAttribute`
  * `listEntry`

### Syntax
#### Primitives
All primitive elements take the form
```xml
<type key="myKey" value="myValue"/>
```
Thus, the syntax for the three primitive is:
```xml
<stringAttribute key="myString" value="aStringValue"/>
<intAttribute key="myInt" value="100"/>
<booleanAttribute key="myBool" value="false"/>
```
The `value` of `stringAttribute` may be any string.  
The `value` of `intAttribute` must be an integer.  
The `value` of `booleanAttribute` must be `true` or `false`.
#### Sets
Sets are an element of type `setAttribute` made up of child elements, each with the type `setEntry`, and each specifying the `value` attribute. Sets do not permit duplicate elements. The `value` of a `setEntry` is not explicitly typed. The expected data is application-specific.
```xml
<setAttribute key="mySet">
    <setEntry value="first"/>
    <setEntry value="second"/>
</setAttribute>
```
#### Maps
Maps are an element of type `mapAttribute` made up of child elements of type `mapEntry` that specify the `key` and `value` attributes. Sets do not permit duplicate elements. The `value` of a `setEntry` is not explicitly typed. The expected data is application-specific.
```xml
<mapAttribute key="myMap">
    <mapEntry key="one" value="first"/>
    <mapEntry key="two" value="second"/>
</mapAttribute>
```
#### Lists
Lists are similar to sets, but with type `listAttribute` and with sub-elements of type `listEntry`. Lists retain their order and permit duplicates. The `value` of a `listEntry` is not explicitly typed. The expected data is application-specific.
```xml
<listAttribute key="myList">
    <listEntry value="first"/>
    <listEntry value="second"/>
</listAttribute>
```