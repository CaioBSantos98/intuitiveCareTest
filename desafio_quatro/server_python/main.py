import fastapi
import csv
from fastapi.middleware.cors import CORSMiddleware

app = fastapi.FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:5173"], 
    allow_credentials=True,
    allow_methods=["*"],  
    allow_headers=["*"],  
)

def read_csv():
    operadoras = []
    with open("data/operadoras.csv", mode="r", encoding="utf-8") as file:
        reader = csv.DictReader(file)
        for row in reader:
            operadoras.append(row)
    return operadoras

dados_operadoras = read_csv();

def csv_para_json(csv_string):
    return csv_string


@app.get("/search")
def read_operadoras(query: str):
    results = []
    
    for operadora in dados_operadoras:
        if query.lower() in str(operadora.values()).lower():
            results.append(operadora)

    return { "Texto da busca": query, "Resultados": results }


