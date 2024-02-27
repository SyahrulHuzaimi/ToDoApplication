import {
  CanActivateFn,
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const userRoutes: string[] = ['/dashboard'];
  const adminRoutes: string[] = ["/admin"];

  const loggedIn = authService.loggedInValue;
  const isAdmin = authService.adminValue;

  console.log(loggedIn);
  console.log(userRoutes.includes(state.url));
  console.log(userRoutes.includes(state.url) && loggedIn);

  if(adminRoutes.includes(state.url) && !isAdmin){
    alert("Cant enter admin page!");
    return router.navigate(['/']);
  }
  if(userRoutes.includes(state.url) && !loggedIn){
    alert("Cant enter wihout login!");
    return router.navigate(['/']);
  }
  
  return true;
};
