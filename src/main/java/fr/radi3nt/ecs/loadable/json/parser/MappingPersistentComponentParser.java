package fr.radi3nt.ecs.loadable.json.parser;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.values.JsonCustomValuesParser;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.ecs.persistence.data.MappedPersistentData;
import fr.radi3nt.json.JsonObject;
import fr.radi3nt.json.JsonValue;

import java.util.HashMap;
import java.util.Map;

public class MappingPersistentComponentParser implements PersistentComponentParser {

    private final char nestingSeparatorChar;
    private final JsonCustomValuesParser customValues;

    public MappingPersistentComponentParser(char nestingSeparatorChar, JsonCustomValuesParser customValues) {
        this.nestingSeparatorChar = nestingSeparatorChar;
        this.customValues = customValues;
    }

    @Override
    public MappedPersistentData parse(JsonObject jsonObject, VariableStorage variableStorage) throws JsonComponentParseException {
        Map<String, Object> keyValue = new HashMap<>();
        addEntries(keyValue, "", jsonObject, variableStorage);
        return new MappedPersistentData(keyValue);
    }

    private void addEntries(Map<String, Object> keyValue, String currentPath, JsonObject object, VariableStorage variableStorage) throws JsonComponentParseException {
        for (JsonObject.Member member : object) {
            String id = currentPath + member.getName();
            JsonValue value = member.getValue();

            if (customValues.isCustomValue(id)) {
                keyValue.put(id, customValues.parseCustom(value, id, variableStorage));
                continue;
            }

            if (value.isObject()) {
                addEntries(keyValue, id + nestingSeparatorChar, value.asObject(), variableStorage);
            } else {
                System.err.println("Value at path " + id + " is not registered, skipping");
            }
        }
    }

}
