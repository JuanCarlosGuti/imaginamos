#!/usr/bin/env bash
# wait-for-it.sh

host="$1"
shift
cmd="$@"

until nc -z $host; do
  >&2 echo "⏳ Esperando a que $host esté disponible..."
  sleep 2
done

>&2 echo "✅ $host está listo — ejecutando el servicio"
exec $cmd
