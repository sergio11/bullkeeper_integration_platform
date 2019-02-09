# BullKeeper Integration Platform
BullKeeper Integration Platform. EIPs patterns with Spring Integration

tr '\n' ',' < app_models_dump_old.json > app_models_dump.json
truncate -s-1 app_models_dump.json
sed -i '1s/^/[ /' app_models_dump.json
echo "]" >> app_models_dump.json

