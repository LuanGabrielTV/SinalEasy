import { Sinal } from "./Sinal";

export class Cidade{
    id: string | undefined;
    nome: string | undefined;
    estado: string | undefined;
    avaliacao: number | undefined;
    sinais: Array<Sinal> | undefined;
}