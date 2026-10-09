"""Validate sheet shape; release mode also requires verified integration cells."""
import json
from pathlib import Path
import sys

root = Path(__file__).resolve().parents[1]
errors = []
for path in sorted((root / 'design').glob('*.json')):
    rows = json.loads(path.read_text())
    columns = set(rows[0]) if rows else set()
    ids = set()
    for row in rows:
        label = f'{path.stem}/{row.get("id", "missing-id")}'
        if set(row) != columns:
            errors.append(f'{label}: inconsistent columns')
        if row.get('id') in ids:
            errors.append(f'{label}: duplicate id')
        ids.add(row.get('id'))
        for key, value in row.items():
            if value is None or value == '':
                errors.append(f'{label}/{key}: unfilled')
        if '--release' in sys.argv and row.get('verified') is False:
            errors.append(f'{label}: unverified integration')
for error in errors:
    print(error)
print(f'Preflight: {len(errors)} issue(s)')
sys.exit(bool(errors))
