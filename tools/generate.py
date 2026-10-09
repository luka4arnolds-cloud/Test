"""Generate one Java tuning record per handling row after preflight."""
import json
from pathlib import Path
import subprocess
import sys

root = Path(__file__).resolve().parents[1]
subprocess.run([sys.executable, str(root / 'tools/preflight.py')], check=True)
rows = json.loads((root / 'design/handling.json').read_text())
columns = [key for key in rows[0] if key != 'id']
declaration = ', '.join('double ' + key for key in columns)
source = 'public record Tuning(' + declaration + ') {\n'
for row in rows:
    values = ', '.join(str(row[key]) for key in columns)
    source += '    public static final Tuning ' + row['id'].upper() + ' = new Tuning(' + values + ');\n'
source += '}\n'
(root / 'src/core/Tuning.java').write_text(source)
