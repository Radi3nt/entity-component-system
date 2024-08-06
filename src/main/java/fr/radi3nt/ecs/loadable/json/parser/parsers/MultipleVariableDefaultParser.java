package fr.radi3nt.ecs.loadable.json.parser.parsers;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.PersistentComponentParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.JsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.ecs.persistence.data.MappedPersistentData;
import fr.radi3nt.ecs.persistence.data.PersistentData;
import fr.radi3nt.json.JsonValue;

import java.util.HashMap;
import java.util.Map;

public class MultipleVariableDefaultParser implements PersistentComponentParser {

    private final Map<String, JsonValueParser> jsonValueParserMap;

    public MultipleVariableDefaultParser(Map<String, JsonValueParser> jsonValueParserMap) {
        this.jsonValueParserMap = jsonValueParserMap;
    }

    @Override
    public PersistentData parse(JsonValue jsonObject, VariableStorage variableStorage) throws JsonComponentParseException {
        if (!jsonObject.isObject())
            return new MappedPersistentData(new HashMap<>());
        Map<String, Object> data = new HashMap<>();
        for (Map.Entry<String, JsonValueParser> stringJsonValueParserEntry : jsonValueParserMap.entrySet()) {
            String name = stringJsonValueParserEntry.getKey();
            data.put(name, stringJsonValueParserEntry.getValue().parseCustom(jsonObject.asObject().get(name), name, variableStorage));
        }
        return new MappedPersistentData(data);
    }
}
