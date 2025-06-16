# Cargar variables del archivo .env al entorno actual del proceso
Get-Content .env | ForEach-Object {
    # Ignorar líneas vacías o comentarios
    if ($_ -match "^\s*$" -or $_ -match "^\s*#") {
        return
    }

    # Coincidir líneas tipo CLAVE=VALOR
    if ($_ -match "^\s*([^#=]+?)\s*=\s*(.+)\s*$") {
        $key = $matches[1].Trim()
        $val = $matches[2].Trim('"').Trim()  # quita comillas si las hay
        [System.Environment]::SetEnvironmentVariable($key, $val, "Process")
    }
}
