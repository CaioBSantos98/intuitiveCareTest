import fastapi
import csv

app = fastapi.FastAPI()

def read_csv():
    operadoras = []
    with open("data/operadoras.csv", mode="r", encoding="utf-8") as file:
        reader = csv.DictReader(file)
        for row in reader:
            operadoras.append(row)
    return operadoras

dados_operadoras = read_csv();

@app.get("/search")
def read_operadoras(query: str):
    results = []
    
    for operadora in dados_operadoras:
        if query.lower() in str(operadora.values()).lower():
            results.append(operadora)

    return { "Texto da busca": query, "Resultados": results }


