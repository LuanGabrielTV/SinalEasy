import { Sinal } from "./Sinal";

export class Cidade{
    nome: string | undefined;
    estado: string | undefined;
    avaliacao: number | undefined;
    sinais: Array<Sinal> | undefined;
}