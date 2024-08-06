
# Entity Component System

ECS interface and implementation in Java

## Features

- Loading entities from json files
- Dynamically mutate entities at runtime
- Iterate over a specific set of components only
- Free to update your systems in any way you want (note that you can use `SystemScheduler` class)
## Roadmap

- More efficient ECS implementation using sparse sets


## Installation

Install with maven; you'll need one dependency, which you can get by clicking on the link below:

- [JavaUtil](https://github.com/Radi3nt/JavaUtil)

(More precisely the JsonHelper module)
## Usage/Examples

Example component class:

```java
public class HealthComponent extends EntityComponent {

    @ComponentFieldPersistent
    public float health;

    @ComponentBuilderPersistent
    public HealthComponent(
            @ComponentParameterFieldPersistent(fieldName = "health")
            float health
    ) {
        this.health = health;
    }

    public HealthComponent() {
    }
}

```

Example system class:

```java
public class DeathOnHealthReachesZeroSystem {

    private final ECSWorld world;

    public DeathOnHealthReachesZeroSystem(ECSWorld world) {
        this.world = world;
    }

    public void update() {
        for (Iterator<DeathOnHealthReachesZeroComponent> it = world.getComponentIterator(DeathOnHealthReachesZeroComponent.class); it.hasNext(); ) {
            DeathOnHealthReachesZeroComponent component = it.next();
            if (component.isEnabled()) {
                ECSEntity current = component.current;
                HealthComponent healthComponent = current.getComponent(HealthComponent.class);
                if (healthComponent.health<=0)
                    current.getComponent(DeathStateComponent.class).dead = true;
            }
        }
    }
}
```

Putting it together (+ custom value parser example)

```java
public class MainECSDemo {

    public static void main(String[] args) throws IOException, ComponentPersistenceException, JsonComponentParseException {
        Map<Class<?>, JsonValueParser> parserMap = new HashMap<>();
        ClassJsonValueParserRegistry parserRegistry = new MapClassJsonValueParserRegistry(parserMap);

        MapClassJsonValueParserRegistry.putNativeParsers(parserMap);
        parserMap.put(DynamicsConstants.class, (value, path, storage) -> {
            if (value.isArray()) {
                ArrayJsonValueParser parser = new ArrayJsonValueParser(float.class, new EncapsulatingVariableJsonValueParser(FloatJsonValueParser.INSTANCE));
                float[] array = (float[]) parser.parseCustom(value, path, storage);
                return new DynamicsConstants(array[0], array[1], array[2]);
            } else if (value.isObject()) {
                MappedPersistentData data = new MappingPersistentComponentParser('.', new JsonCustomValuesParser(JsonCustomValuesParser.fromFieldsAndBuilders(parserRegistry, DynamicsConstants.class))).parse(value.asObject(), storage);
                return new DynamicsConstants((float) data.get("f"), (float) data.get("z"), (float) data.get("r"));
            }
            return null;
        });

        Map<String, ComponentPersistenceType> componentPersistenceTypeMap = new HashMap<>();
        componentPersistenceTypeMap.put("health", ComponentPersistenceType.fromReflection(parserRegistry, HealthComponent.class));
        componentPersistenceTypeMap.put("maxHealth", ComponentPersistenceType.fromReflection(parserRegistry, MaxHealthComponent.class));
        componentPersistenceTypeMap.put("deathState", ComponentPersistenceType.fromReflection(parserRegistry, DeathStateComponent.class));
        componentPersistenceTypeMap.put("deathOnHealthReachesZero", ComponentPersistenceType.fromReflection(parserRegistry, DeathOnHealthReachesZeroComponent.class));
        componentPersistenceTypeMap.put("movement", ComponentPersistenceType.fromReflection(parserRegistry, MovementComponent.class));

        MapComponentBlueprintRegistry blueprintRegistry = new MapComponentBlueprintRegistry(componentPersistenceTypeMap);

        JsonEntityBlueprintLoader loader = new JsonEntityBlueprintLoader(blueprintRegistry);

        JsonValue object = Json.parse(new InputStreamReader(MainECSDemo.class.getResourceAsStream("/demo_entity.json")));
        EntityBlueprint blueprint = loader.load(object.asObject());

        ECSWorld world = new ListingECSWorld();
        ECSEntity entity = blueprint.create(world);
        entity.getComponent(HealthComponent.class).health = 0;

        DeathOnHealthReachesZeroSystem onHealthReachesZeroSystem = new DeathOnHealthReachesZeroSystem(world);
        onHealthReachesZeroSystem.update();
    }

}

```
## Support

For support, email pro.radi3nt@gmail.com or send a message on discord to @radi3nt.


## Authors

- [@radi3nt](https://github.com/Radi3nt)

