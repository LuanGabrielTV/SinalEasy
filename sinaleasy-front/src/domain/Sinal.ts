import { Tipo } from "./Tipo";

export class Sinal{
    nome: string | undefined;
    data: Date | undefined;
    endereco: string | undefined;
    descricao: string | undefined;
    tipos: Array<Tipo> | undefined;
    lat: number | undefined;
    long: number | undefined;
    tamanho: number | undefined;
}