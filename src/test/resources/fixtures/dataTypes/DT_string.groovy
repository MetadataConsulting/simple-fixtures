import org.modelcatalogue.fixtures.DataType

load("dataTypes/DT_double")

fixture {
    DT_string(DataType, name: "string", description: "a string")
}