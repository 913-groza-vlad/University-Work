import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError} from 'rxjs/operators';
import { Document } from './document';

@Injectable({
  providedIn: 'root'
})
export class GenericService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  private backendUrl = 'http://localhost/Documents%20-%20Angular/backend-php/controller/controller.php';

  constructor(private http: HttpClient) { }

  fetchDocuments(type: string, format: string): Observable<Document[]> {
    return this.http.get<Document[]>(this.backendUrl + '?action=filterDocuments&type=' + type + '&format=' + format)
          .pipe(catchError(this.handleError<Document[]>('fetchDocuments', [])));
  }

  private handleError<T>(operation = 'operation', result?: T): (error: any) => Observable<T> {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

  deleteDocument(documentId: number): Observable<any> {
    return this.http.get<string>(this.backendUrl + '?action=deleteDocument&id=' + documentId)
    .pipe(catchError(this.handleError<string>('deleteDocument', "")));;
  }

  addDocument(author: string, title: string, number_of_pages: number, type: string, format: string): Observable<any> {
    let url = `${this.backendUrl}?action=addDocument&author=${author}&title=${title}&number_of_pages=${number_of_pages}&type=${type}&format=${format}`
    return this.http.get<string>(url).pipe(catchError(this.handleError<string>('addDocument', "")));
  }

  updateDocument(id: number, author: string, title: string, number_of_pages: number,
    type: string, format: string): Observable<any> {
      let url = `${this.backendUrl}?action=updateDocument&id=${id}&author=${author}&title=${title}&number_of_pages=${number_of_pages}&type=${type}&format=${format}`
      return this.http.get<string>(url).pipe(catchError(this.handleError<string>('updateDocument', "")));
  }

  getDocument(id: number): Observable<Document> {
    return this.http.get<Document>(this.backendUrl + '?action=getDocument&id=' + id)
          .pipe(catchError(this.handleError<Document>('getDocument')));
  }
}
