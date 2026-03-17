# File Converter

A powerful Java CLI tool for converting files between JSON, XML, and CSV formats.

## Features

- **Bidirectional conversion**: Convert between any of the supported formats
- **Auto-detection**: Automatically detects input/output formats from file extensions
- **Manual override**: Specify formats manually when needed
- **Unicode support**: Full UTF-8 encoding support
- **Error handling**: Comprehensive error messages and validation
- **Design patterns**: Implements Factory, Strategy, and Facade patterns
- **Well-tested**: Comprehensive unit tests with high coverage

## Supported Formats

- **JSON** (`.json`)
- **XML** (`.xml`)
- **CSV** (`.csv`)

## Requirements

- Java 21 or higher
- Maven 3.6 or higher

## Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd java-converter
```

2. Build the project:
```bash
mvn clean package
```

3. The executable JAR will be created at: `target/file-converter-1.0.0.jar`

## Usage

### Basic Usage

```bash
java -jar target/file-converter-1.0.0.jar <input_file> <output_file>
```

The converter automatically detects formats from file extensions:

```bash
# Convert JSON to XML
java -jar target/file-converter-1.0.0.jar data.json output.xml

# Convert CSV to JSON
java -jar target/file-converter-1.0.0.jar input.csv output.json

# Convert XML to CSV
java -jar target/file-converter-1.0.0.jar data.xml output.csv
```

### Advanced Usage

Specify formats manually:

```bash
java -jar target/file-converter-1.0.0.jar input.txt output.json --input-format xml --output-format json
```

### Command Line Options

- `<input_file>`: Path to the input file (required)
- `<output_file>`: Path to the output file (required)
- `-i, --input-format`: Specify input format (json, xml, csv)
- `-o, --output-format`: Specify output format (json, xml, csv)
- `-v, --verbose`: Enable verbose output
- `-h, --help`: Show help message
- `--version`: Show version information

### Examples

#### JSON to XML
```bash
java -jar target/file-converter-1.0.0.jar users.json users.xml
```

Input (`users.json`):
```json
{
  "users": [
    {
      "name": "John Doe",
      "email": "john@example.com",
      "age": 30
    },
    {
      "name": "Jane Smith",
      "email": "jane@example.com",
      "age": 25
    }
  ]
}
```

Output (`users.xml`):
```xml
<?xml version="1.0" encoding="UTF-8"?>
<root>
  <users>
    <item_0>
      <name>John Doe</name>
      <email>john@example.com</email>
      <age>30</age>
    </item_0>
    <item_1>
      <name>Jane Smith</name>
      <email>jane@example.com</email>
      <age>25</age>
    </item_1>
  </users>
</root>
```

#### CSV to JSON
```bash
java -jar target/file-converter-1.0.0.jar products.csv products.json
```

Input (`products.csv`):
```csv
name,price,category
Laptop,999.99,Electronics
Book,19.99,Education
Coffee,4.99,Food
```

Output (`products.json`):
```json
[ {
  "name" : "Laptop",
  "price" : "999.99",
  "category" : "Electronics"
}, {
  "name" : "Book",
  "price" : "19.99",
  "category" : "Education"
}, {
  "name" : "Coffee",
  "price" : "4.99",
  "category" : "Food"
} ]
```

#### XML to CSV
```bash
java -jar target/file-converter-1.0.0.jar inventory.xml inventory.csv
```

## Architecture

The project follows clean architecture principles and implements several design patterns:

### Design Patterns

1. **Factory Pattern**: `ParserFactory` and `GeneratorFactory` create appropriate parsers and generators based on file format.

2. **Strategy Pattern**: `ConversionStrategy` interface allows different conversion strategies to be implemented.

3. **Facade Pattern**: `FileConverterFacade` provides a simplified interface for file conversion operations.

### Package Structure

```
com.fileconverter/
├── cli/                    # CLI interface and facade
├── conversion/            # Conversion strategies
├── exception/             # Custom exceptions
├── generator/             # File generators (JSON, XML, CSV)
├── parser/               # File parsers (JSON, XML, CSV)
└── utils/                 # Utility classes
```

## Testing

Run the test suite:

```bash
mvn test
```

Run tests with coverage:

```bash
mvn clean test jacoco:report
```

The test coverage report will be generated at `target/site/jacoco/index.html`.

## Error Handling

The converter provides detailed error messages for:
- Unsupported file formats
- Invalid file syntax
- Missing files
- Permission issues
- Encoding problems

## Dependencies

- **Jackson**: JSON and XML processing
- **OpenCSV**: CSV processing
- **Picocli**: Command line interface
- **JUnit 5**: Unit testing
- **Mockito**: Mocking for tests

## Building

### Development Build

```bash
mvn clean compile
```

### Production Build

```bash
mvn clean package
```

### Skip Tests

```bash
mvn clean package -DskipTests
```

## License

This project is open source and available under the MIT License.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Run the test suite
6. Submit a pull request

## Troubleshooting

### Common Issues

1. **"Could not detect input file format"**
   - Ensure the input file has a supported extension (.json, .xml, .csv)
   - Or specify the format manually using `--input-format`

2. **"Cannot read input file"**
   - Check file permissions
   - Ensure the file exists and is readable

3. **"Failed to parse JSON/XML/CSV file"**
   - Verify the file syntax is valid
   - Check for encoding issues

4. **Memory issues with large files**
   - Increase JVM heap size: `-Xmx2g`
   - Consider processing files in chunks (future enhancement)
